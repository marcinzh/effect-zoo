package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import cats.{Monad, Eval}
import cats.data.{ReaderWriterStateT, EitherT}
import cats.implicits._
import cats.mtl.{Ask, Tell, Stateful, Raise}


object CatsMTL_RWS extends Sumh.Entry(Contender.CatsMTL % "RWS"):
  def prog[F[_]: Monad](using S: Stateful[F, Int], R: Ask[F, Int], W: Tell[F, Long]): F[Int] =
    for
      s <- S.get
      _ <- S.set(s + 1)
      _ <- W.tell(s.toLong)
      r <- R.ask
      x <-
        if s < r
        then prog
        else s.pure
    yield x

  override def round1 =
    type MyEither[A] = EitherT[Eval, String, A]
    type MyRWS[A] = ReaderWriterStateT[MyEither, Int, Long, Int, A]

    prog[MyRWS]
    .run(Sumh.LIMIT, 0) // run RWS
    .value              // run Either
    .value              // run Eval
    .map { case (w, s, a) => (a, w, s) }

