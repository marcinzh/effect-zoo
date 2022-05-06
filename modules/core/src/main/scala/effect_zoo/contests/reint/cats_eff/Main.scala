package effect_zoo.contests.reint.cats_eff
import effect_zoo.contests.{Reint, Contender}
import scala.util.chaining._
import cats._
import cats.data._
import cats.implicits._
import org.atnos.eff._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._


object Main extends Reint.Entry(Contender.CatsEff):
  def prog(n: Int): (Vector[String], Vector[String]) =
    type F[A] = Eff[Fx.fx5[Query, Http, Logging, ResponseReader, LogWriter], A]
    Monad[F].replicateA(n, Query.listFruits)
    .map(_.iterator.flatten.toVector)
    .pipe(toLoggedHttp)
    .pipe(accumulateLogMessages)
    .pipe(mockResponses)
    .runWriterMonoid
    .runReader(Reint.Shared.RESPONSE)
    .run


  override def round1 = Reint.Shared.rep1(prog)
  override def round2 = Reint.Shared.rep2(prog)
  override def round3 = Reint.Shared.rep3(prog)
