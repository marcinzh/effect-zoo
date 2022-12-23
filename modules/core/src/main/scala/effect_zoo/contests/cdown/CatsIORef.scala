package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import cats.Monad
import cats.implicits._
import cats.mtl.Ask
import cats.data.ReaderT
import cats.effect.{IO, LiftIO, Ref}
import cats.effect.unsafe.implicits.global


object CatsIORef extends Cdown.Entry(Contender.CatsIO % "Ref"):
  def program[F[_]: Monad](using L: LiftIO[F], R: Ask[F, Ref[IO, Int]]): F[Int] =
    R.ask.flatMap { ref =>
      L.liftIO(ref.get).flatMap { n =>
        if n <= 0
        then n.pure
        else L.liftIO(ref.set(n - 1)) *> program
      }
    }

  override def round1 =
    type Eff[A] = ReaderT[IO, Ref[IO, Int], A]
    (for
      ref <- IO.ref(Cdown.LIMIT)
      // a_s <- program[Eff].run(ref) <*> ref.get
      a <- program[Eff].run(ref)
      s <- ref.get
    yield (a, s))
    .unsafeRunSync()
