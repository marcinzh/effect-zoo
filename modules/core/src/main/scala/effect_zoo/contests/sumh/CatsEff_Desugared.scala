package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import cats._
import cats.data._
import cats.implicits._
import org.atnos.eff._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._


object CatsEff_Desugared extends Sumh.Entry(Contender.CatsEff % "Desugared"):
  type MyEither[A] = Either[String, A]
  type MyReader[A] = Reader[Int, A]
  type MyWriter[A] = Writer[Long, A]
  type MyState[A] = State[Int, A]

  def prog[U](using MyReader |= U, MyWriter |= U, MyState |= U, MyEither |= U): Eff[U, Int] =
    get.flatMap: s =>
      put(s + 1).flatMap: _ =>
        tell(s.toLong).flatMap: _ =>
          ask.flatMap: r =>
            if s < r
            then prog
            else Eff.pure(s)


  override def round1 =
    type MyEff = Fx.fx4[MyReader, MyWriter, MyState, MyEither]

    prog[MyEff]
    .runState(0)
    .runWriterMonoid
    .runReader(Sumh.LIMIT)
    .runEither
    .run
    .map { case ((a, s), w) => (a, w, s) }
