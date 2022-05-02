package effect_zoo.contests.fibo
import effect_zoo.contests.{Fibo, Contender}
import scala.util.chaining._
import cats.Monoid
import cats.instances.int._
import zio._
import effect_zoo.aux.zio_.BenchmarkRuntime
import effect_zoo.aux.zio_.rws.mono.{Reader, Writer, State}


object ZioMono extends Fibo.Entry(Contender.ZIO % "Mono"):
  object MyReader extends Reader[Int]
  object MyWriter extends Writer[Int]
  object MyState extends State[Int]
  type MyReader = Has[MyReader.Service]
  type MyWriter = Has[MyWriter.Service]
  type MyState = Has[MyState.Service]


  def fibo(a: Int): ZIO[MyReader & MyWriter & MyState, String, Int] =
    for
      b <- MyState.get
      _ <- MyState.put(a)
      c = a + b
      _ <- MyWriter.tell(c)
      d <- MyReader.ask
      e <-
        if c < d
        then fibo(c)
        else ZIO.succeed(c)
    yield e


  override def round1 =
    (MyWriter.listen(fibo(1)) <*> MyState.get)
    .map { case ((a, w), s) => (a, w, s) }
    .provideLayer(
      MyState.Live.layer(0) ++
      MyWriter.Live.layer ++
      MyReader.Live.layer(Fibo.LIMIT)
    )
    .either
    .pipe(BenchmarkRuntime.unsafeRun)
