package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import scala.util.chaining._
import cats.Monoid
import cats.syntax.semigroup._
import cats.instances.int._
import zio._
import effect_zoo.auxx.zio_.BenchmarkRuntime
import effect_zoo.auxx.zio_.rws.stateful.{Reader, ZSReader, Writer, ZSWriter, State, ZSState}


object ZioStateful extends Sumh.Entry(Contender.ZIO % "Stateful"):
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
    (Writer.listen[Long](prog) <*> State.get[Int])
    .pipe(ZIO.stateful[State[Int] & Writer[Long]](ZSReader(Sumh.LIMIT)))
    .pipe(ZIO.stateful[State[Int]](ZSWriter(0L)))
    .pipe(ZIO.stateful[Any](ZSState(0)))
    .either
    .pipe(BenchmarkRuntime.unsafeRun)
