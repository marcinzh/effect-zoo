package effect_zoo.contests.fibo
import effect_zoo.contests.{Fibo, Contender}
import cats.{Monoid, Eval, Now}
import cats.data.{ReaderWriterStateT, EitherT}
import cats.implicits._


object CatsCore_RWS extends Fibo.Entry(Contender.CatsCore % "RWS"):
  val MyEffectStack = CatsCore_RWS_Aux.EffectStack[String, Int, Int, Int]
  import MyEffectStack._

  def fibo(a: Int): Eff[Int] =
    for
      b <- liftRWS(ReaderWriterStateT.get)
      _ <- liftRWS(ReaderWriterStateT.set(a))
      c = a + b
      _ <- liftRWS(ReaderWriterStateT.tell(c))
      d <- liftRWS(ReaderWriterStateT.ask)
      e <-
        if c < d
        then fibo(c)
        else liftV(Now(c))
    yield e

  override def round1 =
    fibo(1)
    .run(Fibo.LIMIT, 0) // run RWS
    .value              // run Either
    .value              // run Eval
    .map { case (w, s, a) => (a, w, s) }


object CatsCore_RWS_Aux:
  class EffectStack[E, R, W: Monoid, S]:
    type Eff2[A] = Eval[A]
    type Eff1[A] = EitherT[Eff2, E, A]
    type Eff[A] = ReaderWriterStateT[Eff1, R, W, S, A]

    def liftRWS[A](op: Eff[A]): Eff[A] = op
    def liftE[A](op: Eff1[A]): Eff[A] = ReaderWriterStateT.liftF(op)
    def liftV[A](op: Eff2[A]): Eff[A] = liftE(EitherT.liftF(op))
