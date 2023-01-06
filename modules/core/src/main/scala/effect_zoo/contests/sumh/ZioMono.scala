package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import scala.util.chaining._
import cats.Monoid
import cats.instances.int._
import zio._
import effect_zoo.auxx.zio_.BenchmarkRuntime
import effect_zoo.auxx.zio_.rws.mono.{Reader, Writer, State}


object ZioMono extends Sumh.Entry(Contender.ZIO % "Mono"):
  object MyReader extends Reader[Int]
  object MyWriter extends Writer[Long]
  object MyState extends State[Int]
  type MyReader = MyReader.Service
  type MyWriter = MyWriter.Service
  type MyState = MyState.Service


  def prog: ZIO[MyReader & MyWriter & MyState, String, Int] =
    for
      s <- MyState.get
      _ <- MyState.put(s + 1)
      _ <- MyWriter.tell(s)
      r <- MyReader.ask
      x <-
        if s < r
        then prog
        else ZIO.succeed(s)
    yield x


  override def round1 =
    (MyWriter.listen(prog) <*> MyState.get)
    .provideLayer(
      MyState.Live.layer(0) ++
      MyWriter.Live.layer ++
      MyReader.Live.layer(Sumh.LIMIT)
    )
    .either
    .pipe(BenchmarkRuntime.unsafeRun)
