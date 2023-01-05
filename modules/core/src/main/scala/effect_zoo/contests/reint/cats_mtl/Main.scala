package effect_zoo.contests.reint.cats_mtl
import effect_zoo.contests.{Reint, Contender}
import cats._
import cats.implicits._
import cats.data.{ReaderT, WriterT}
import cats.mtl.{Ask, Tell}


object Main extends Reint.Entry(Contender.CatsMTL):
  def prog(n: Int): (Vector[String], Vector[String]) =
    type F[A] = ReaderT[WriterT[Eval, Vector[String], _], String, A]

    val query =
      val http = mockResponses[F]
      val logging = accumulateLogMessages[F]
      toLoggedHttp(http, logging)

    Monad[F].replicateA(n, query.listFruits)
    .map(_.iterator.toVector.flatten)
    .run(Reint.Shared.RESPONSE)
    .run
    .value
    .swap


  override def round1 = Reint.Shared.rep1(prog)
  override def round2 = Reint.Shared.rep2(prog)
  override def round3 = Reint.Shared.rep3(prog)
