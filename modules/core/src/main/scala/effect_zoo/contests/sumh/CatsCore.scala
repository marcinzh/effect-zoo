package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import cats.{Monoid, Eval, Now}
import cats.data.{ReaderT, WriterT, StateT, EitherT}
import cats.implicits._
import CatsCore_Aux._


object CatsCore extends Sumh.Entry(Contender.CatsCore):
  val MyEffectStack = EffectStack[String, Int, Long, Int]
  import MyEffectStack._

  def prog: Eff[Int] =
    for
      s <- liftS(StateT.get)
      _ <- liftS(StateT.set(s + 1))
      _ <- liftW(WriterT.tell(s))
      r <- liftR(ReaderT.ask)
      x <-
        if s < r
        then prog
        else liftV(Now(s))
    yield x

  override def round1 =
    prog
    .run(0)           // run State
    .run              // run Writer
    .run(Sumh.LIMIT)  // run Reader
    .value            // run Either
    .value            // run Eval
    .map { case (w, (s, a)) => (a, w, s) }


object CatsCore_Aux:
  class EffectStack[E, R, W: Monoid, S]:
    type Eff4[A] = Eval[A]
    type Eff3[A] = EitherT[Eff4, E, A]
    type Eff2[A] = ReaderT[Eff3, R, A]
    type Eff1[A] = WriterT[Eff2, W, A]
    type Eff[A] = StateT[Eff1, S, A]

    def liftS[A](op: Eff[A]): Eff[A] = op
    def liftW[A](op: Eff1[A]): Eff[A] = StateT.liftF(op)
    def liftR[A](op: Eff2[A]): Eff[A] = liftW(WriterT.liftF(op))
    def liftE[A](op: Eff3[A]): Eff[A] = liftR(ReaderT.liftF(op))
    def liftV[A](op: Eff4[A]): Eff[A] = liftE(EitherT.liftF(op))
