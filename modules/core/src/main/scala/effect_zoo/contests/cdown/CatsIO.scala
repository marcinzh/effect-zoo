package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import cats.Monad
import cats.data.StateT
import cats.implicits._
import cats.mtl.Stateful
import cats.effect.IO
import cats.effect.unsafe.implicits.global


object CatsIO extends Cdown.Entry(Contender.CatsIO):
  def program[F[_]: Monad](using S: Stateful[F, Int]): F[Int] =
    S.get.flatMap { n =>
      if n <= 0
      then n.pure
      else S.set(n - 1) *> program
    }

  override def round1 =
    program[StateT[IO, Int, _]]
    .run(Cdown.LIMIT)
    .unsafeRunSync()
