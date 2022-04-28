package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import cats.Monad
import cats.data.State
import cats.implicits._
import cats.mtl.Stateful


object CatsMTL extends Cdown.Entry(Contender.CatsMTL):
  def program[F[_]: Monad](using S: Stateful[F, Int]): F[Int] =
    S.get.flatMap { n =>
      if n <= 0
      then n.pure
      else S.set(n - 1) *> program
    }

  override def round1 = program[State[Int, _]].run(Cdown.LIMIT).value
