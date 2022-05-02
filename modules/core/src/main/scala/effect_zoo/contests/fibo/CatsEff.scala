package effect_zoo.contests.fibo
import effect_zoo.contests.{Fibo, Contender}
import cats._
import cats.data._
import cats.implicits._
import org.atnos.eff._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._


object CatsEff extends Fibo.Entry(Contender.CatsEff):
  type MyEither[A] = Either[String, A]
  type MyReader[A] = Reader[Int, A]
  type MyWriter[A] = Writer[Int, A]
  type MyState[A] = State[Int, A]
  
  def fibo[U](a: Int)(using MyReader |= U, MyWriter |= U, MyState |= U, MyEither |= U): Eff[U, Int] =
    for
      b <- get
      _ <- put(a)
      c = a + b
      _ <- tell(c)
      d <- ask
      e <-
        if c < d
        then fibo(c)
        else Eff.pure(c)
    yield e


  override def round1 =
    type MyEff = Fx.fx4[MyReader, MyWriter, MyState, MyEither]

    fibo[MyEff](1)
    .runState(0)
    .runWriterMonoid
    .runReader(Fibo.LIMIT)
    .runEither
    .run
    .map { case ((a, s), w) => (a, w, s) }
