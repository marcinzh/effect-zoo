package effect_zoo.contests.reint.zio_
import effect_zoo.contests.{Reint, Contender}
import effect_zoo.contests.ZioBenchmarkRuntime
import scala.util.chaining._
import zio._


object Main extends Reint.Entry(Contender.ZIO):
  def prog(n: Int): (Vector[String], Vector[String]) =
    Query.listFruits.replicateM(n)
    .map(_.iterator.flatten.toVector)
    .zip(LogWriter.hear)
    .provideLayer(
      ((
        (ResponseReaderLive.layer(Reint.Shared.RESPONSE) >>> MockResponses.HttpLive.layer) ++
        (LogWriterLive.layer >>> AccumulateLogMessages.LoggingLive.layer)
      ) >>> ToLoggedHttp.QueryLive.layer) ++ LogWriterLive.layer
    )
    .pipe(ZioBenchmarkRuntime.unsafeRun)


  override def round1 = Reint.Shared.rep1(prog)
  override def round2 = Reint.Shared.rep2(prog)
  override def round3 = Reint.Shared.rep3(prog)
