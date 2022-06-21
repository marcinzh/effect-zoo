package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import cats.{Monad, Eval}
import cats.data.{ReaderT, WriterT, StateT, EitherT}
import cats.implicits._
import cats.mtl.{Ask, Tell, Stateful, Raise}


object CatsMTL extends Sumh.Entry(Contender.CatsMTL):
  def prog[F[_]: Monad](using S: Stateful[F, Int], R: Ask[F, Int], W: Tell[F, Long]): F[Int] =
    for
      s <- S.get
      _ <- S.set(s + 1)
      _ <- W.tell(s)
      r <- R.ask
      x <-
        if s < r
        then prog
        else s.pure
    yield x

  override def round1 =
    type MyEither[A] = EitherT[Eval, String, A]
    type MyReader[A] = ReaderT[MyEither, Int, A]
    type MyWriter[A] = WriterT[MyReader, Long, A]
    type MyState[A] = StateT[MyWriter, Int, A]

    prog[MyState]
    .run(0)          // run State
    .run             // run Writer
    .run(Sumh.LIMIT) // run Reader
    .value           // run Either
    .value           // run Eval
    .map { case (w, (s, a)) => (a, w, s) }
