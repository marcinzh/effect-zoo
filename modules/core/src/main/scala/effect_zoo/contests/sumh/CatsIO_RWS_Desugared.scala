package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import cats.Monad
import cats.data.{ReaderWriterStateT, EitherT}
import cats.implicits._
import cats.mtl.{Ask, Tell, Stateful, Raise}
import cats.effect.IO
import cats.effect.unsafe.implicits.global


object CatsIO_RWS_Desugared extends Sumh.Entry(Contender.CatsIO % "RWS_Desugared"):
  def prog[F[_]: Monad](using S: Stateful[F, Int], R: Ask[F, Int], W: Tell[F, Long]): F[Int] =
    S.get.flatMap: s =>
      S.set(s + 1).flatMap: _ =>
        W.tell(s).flatMap: _ =>
          R.ask.flatMap: r =>
            if s < r
            then prog
            else s.pure

  override def round1 =
    type MyEither[A] = EitherT[IO, String, A]
    type MyRWS[A] = ReaderWriterStateT[MyEither, Int, Long, Int, A]

    prog[MyRWS]
    .run(Sumh.LIMIT, 0) // run RWS
    .value              // run Either
    .unsafeRunSync()
    .map { case (w, s, a) => (a, w, s) }

