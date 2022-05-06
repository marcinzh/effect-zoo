package effect_zoo.contests.reint.zio_mono
import effect_zoo.contests.{Reint, Contender}
import scala.util.chaining._
import zio._
import effect_zoo.aux.zio_.BenchmarkRuntime


object Main extends Reint.Entry(Contender.ZIO):
  def prog(n: Int): (Vector[String], Vector[String]) =
    LogWriter.listen(
      Query.listFruits.replicateM(n)
      .map(_.iterator.flatten.toVector)
    )
    .provideLayer(
      ((
        (ResponseReader.Live.layer(Reint.Shared.RESPONSE) >>> MockResponses.HttpLive.layer) ++
        (LogWriter.Live.layer >>> AccumulateLogMessages.LoggingLive.layer)
      ) >>> ToLoggedHttp.QueryLive.layer) ++ LogWriter.Live.layer
    )
    .pipe(BenchmarkRuntime.unsafeRun)


  override def round1 = Reint.Shared.rep1(prog)
  override def round2 = Reint.Shared.rep2(prog)
  override def round3 = Reint.Shared.rep3(prog)
