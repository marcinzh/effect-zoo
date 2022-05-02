package effect_zoo.contests.fibo
import effect_zoo.contests.{Fibo, Contender}
import scala.util.chaining._
import cats.Monoid
import cats.instances.int._
import zio._
import effect_zoo.aux.zio_.BenchmarkRuntime
import effect_zoo.aux.zio_.rws.layer.{Reader, ReaderLive, Writer, WriterLive, State, StateLive}


object ZioLayer extends Fibo.Entry(Contender.ZIO % "Layer"):
  def fibo(a: Int): ZIO[Reader[Int] & Writer[Int] & State[Int], String, Int] =
    for
      b <- State.get[Int]
      _ <- State.put(a)
      c = a + b
      _ <- Writer.tell(c)
      d <- Reader.ask[Int]
      e <-
        if c < d
        then fibo(c)
        else ZIO.succeed(c)
    yield e


  override def round1 =
    (Writer.listen[Int](fibo(1)) <*> State.get[Int])
    .provideLayer(
      StateLive.layer(0) ++
      WriterLive.layer[Int] ++
      ReaderLive.layer(Fibo.LIMIT)
    )
    .either
    .pipe(BenchmarkRuntime.unsafeRun)
