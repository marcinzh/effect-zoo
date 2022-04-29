package effect_zoo.contests.fibo
import effect_zoo.contests.{Fibo, Contender}
import cats.{Monad, Eval}
import cats.data.{ReaderT, WriterT, StateT, EitherT}
import cats.implicits._
import cats.mtl.{Ask, Tell, Stateful, Raise}


object CatsMTL extends Fibo.Entry(Contender.CatsMTL):
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
    type MyReader[A] = ReaderT[MyEither, Int, A]
    type MyWriter[A] = WriterT[MyReader, Int, A]
    type MyState[A] = StateT[MyWriter, Int, A]

    fibo[MyState](1)
    .run(0)          // run State
    .run             // run Writer
    .run(Fibo.LIMIT) // run Reader
    .value           // run Either
    .value           // run Eval
    .map { case (w, (s, a)) => (a, w, s) }
