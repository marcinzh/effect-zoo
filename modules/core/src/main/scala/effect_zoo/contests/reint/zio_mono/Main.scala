package effect_zoo.contests.reint.zio_mono
import effect_zoo.contests.{Reint, Contender}
import scala.util.chaining._
import zio._
import effect_zoo.auxx.zio_.BenchmarkRuntime


object Main extends Reint.Entry(Contender.ZIO):
  def prog(n: Int): (Vector[String], Vector[String]) =
    LogWriter.listen(
      Query.listFruits.replicateM(n)
      .map(_.iterator.flatten.toVector)
    )
    .provideLayer {
      val lw = LogWriter.Live.layer
      ((
        (ResponseReader.Live.layer(Reint.Shared.RESPONSE) >>> MockResponses.layer) ++
        (lw >>> AccumulateLogMessages.layer)
      ) >>> ToLoggedHttp.layer) ++ lw
    }
    .pipe(BenchmarkRuntime.unsafeRun)


  override def round1 = Reint.Shared.rep1(prog)
  override def round2 = Reint.Shared.rep2(prog)
  override def round3 = Reint.Shared.rep3(prog)
