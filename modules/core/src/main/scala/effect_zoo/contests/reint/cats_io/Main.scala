package effect_zoo.contests.reint.cats_io
import effect_zoo.contests.{Reint, Contender}
import cats._
import cats.implicits._
import cats.data.{ReaderT, WriterT}
import cats.mtl.{Ask, Tell}
import cats.effect.IO
import cats.effect.unsafe.implicits.global
/** Reusing modules from 'cats_mtl` */
import effect_zoo.contests.reint.cats_mtl.{mockResponses, accumulateLogMessages, toLoggedHttp}


object Main extends Reint.Entry(Contender.CatsIO):
  def prog(n: Int): (Vector[String], Vector[String]) =
    type F[A] = ReaderT[WriterT[IO, Vector[String], _], String, A]

    val query =
      val http = mockResponses[F]
      val logging = accumulateLogMessages[F]
      toLoggedHttp(http, logging)

    Monad[F].replicateA(n, query.listFruits)
    .map(_.iterator.toVector.flatten)
    .run(Reint.Shared.RESPONSE)
    .run
    .unsafeRunSync()
    .swap


  override def round1 = Reint.Shared.rep1(prog)
  override def round2 = Reint.Shared.rep2(prog)
  override def round3 = Reint.Shared.rep3(prog)
