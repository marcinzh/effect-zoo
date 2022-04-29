package effect_zoo.contests.fibo
import effect_zoo.contests.{Fibo, Contender}
import cats.{Monad, Eval}
import cats.data.{ReaderWriterStateT, EitherT}
import cats.implicits._
import cats.mtl.{Ask, Tell, Stateful, Raise}


object CatsMTL_RWS extends Fibo.Entry(Contender.CatsMTL % "RWS"):
  def fibo[F[_]: Monad](a: Int)(using S: Stateful[F, Int], R: Ask[F, Int], W: Tell[F, Int]): F[Int] =
    for
      b <- S.get
      _ <- S.set(a)
      c = a + b
      _ <- W.tell(c)
      d <- R.ask
      e <-
        if c < d
        then fibo(c)
        else c.pure
    yield e

  override def round1 =
    type MyEither[A] = EitherT[Eval, String, A]
    type MyRWS[A] = ReaderWriterStateT[MyEither, Int, Int, Int, A]

    fibo[MyRWS](1)
    .run(Fibo.LIMIT, 0) // run RWS
    .value              // run Either
    .value              // run Eval
    .map { case (w, s, a) => (a, w, s) }

