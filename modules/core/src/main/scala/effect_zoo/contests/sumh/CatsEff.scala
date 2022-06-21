package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import cats._
import cats.data._
import cats.implicits._
import org.atnos.eff._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._


object CatsEff extends Sumh.Entry(Contender.CatsEff):
  type MyEither[A] = Either[String, A]
  type MyReader[A] = Reader[Int, A]
  type MyWriter[A] = Writer[Long, A]
  type MyState[A] = State[Int, A]
  
  def prog[U](using MyReader |= U, MyWriter |= U, MyState |= U, MyEither |= U): Eff[U, Int] =
    for
      s <- get
      _ <- put(s + 1)
      _ <- tell(s.toLong)
      r <- ask
      x <-
        if s < r
        then prog
        else Eff.pure(s)
    yield x


  override def round1 =
    type MyEff = Fx.fx4[MyReader, MyWriter, MyState, MyEither]

    prog[MyEff]
    .runState(0)
    .runWriterMonoid
    .runReader(Sumh.LIMIT)
    .runEither
    .run
    .map { case ((a, s), w) => (a, w, s) }
