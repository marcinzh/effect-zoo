package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import scala.util.chaining._
import cats.Monoid
import cats.syntax.semigroup._
import cats.instances.int._
import zio._
import effect_zoo.auxx.zio_.BenchmarkRuntime
import effect_zoo.auxx.zio_.rws.cake.{Reader, Writer, State, Cake}


object ZioCake extends Sumh.Entry(Contender.ZIO % "Cake"):
  def prog: ZIO[Reader[Int] & Writer[Long] & State[Int], String, Int] =
    for
      s <- State.get[Int]
      _ <- State.put(s + 1)
      _ <- Writer.tell(s.toLong)
      r <- Reader.ask[Int]
      x <-
        if s < r
        then prog
        else ZIO.succeed(s)
    yield x


  override def round1 =
    (for
      cake <- Cake[Int, Long, Int](Sumh.LIMIT, 0) 
      prog2 = Writer.listen[Long](prog) <*> State.get[Int]
      aws <- prog2.provide(cake)
      ((a, w), s) = aws
    yield (a, w, s))
    .either
    .pipe(BenchmarkRuntime.unsafeRun)
