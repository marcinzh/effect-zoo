package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import cats.{Monoid, Eval, Now}
import cats.data.{ReaderWriterStateT, EitherT}
import cats.implicits._


object CatsCore_RWS extends Sumh.Entry(Contender.CatsCore % "RWS"):
  val MyEffectStack = CatsCore_RWS_Aux.EffectStack[String, Int, Long, Int]
  import MyEffectStack._

  def prog: Eff[Int] =
    for
      s <- liftRWS(ReaderWriterStateT.get)
      _ <- liftRWS(ReaderWriterStateT.set(s + 1))
      _ <- liftRWS(ReaderWriterStateT.tell(s))
      r <- liftRWS(ReaderWriterStateT.ask)
      x <-
        if s < r
        then prog
        else liftV(Now(s))
    yield x

  override def round1 =
    prog
    .run(Sumh.LIMIT, 0) // run RWS
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
