package effect_zoo.contests.fibo
import effect_zoo.contests.{Fibo, Contender}
import scala.util.chaining._
import cats.Monoid
import cats.syntax.semigroup._
import cats.instances.int._
import zio._
import effect_zoo.aux.zio_.BenchmarkRuntime
import effect_zoo.aux.zio_.rws.stateful.{Reader, ZSReader, Writer, ZSWriter, State, ZSState}


object ZioStateful extends Fibo.Entry(Contender.ZIO % "Stateful"):
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
    .pipe(ZIO.stateful[State[Int] & Writer[Int]](ZSReader(Fibo.LIMIT)))
    .pipe(ZIO.stateful[State[Int]](ZSWriter(0)))
    .pipe(ZIO.stateful[Any](ZSState(0)))
    .either
    .pipe(BenchmarkRuntime.unsafeRun)
