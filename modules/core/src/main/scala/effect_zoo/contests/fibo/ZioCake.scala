package effect_zoo.contests.fibo
import effect_zoo.contests.{Fibo, Contender}
import scala.util.chaining._
import cats.Monoid
import cats.syntax.semigroup._
import cats.instances.int._
import zio._
import effect_zoo.aux.zio_.BenchmarkRuntime
import effect_zoo.aux.zio_.rws.cake.{Reader, Writer, State, Cake}


object ZioCake extends Fibo.Entry(Contender.ZIO % "Cake"):
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
    (for
      cake <- Cake(Fibo.LIMIT, 0) 
      prog = Writer.listen(fibo(1)) <*> State.get[Int]
      aws <- prog.provide(cake)
      ((a, w), s) = aws
    yield (a, w, s))
    .either
    .pipe(BenchmarkRuntime.unsafeRun)
