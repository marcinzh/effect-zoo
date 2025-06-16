package effect_zoo.contests.reint.zio_layer
import effect_zoo.contests.{Reint, Contender}
import zio._
import effect_zoo.auxx.UnsafeRunZio._
import effect_zoo.auxx.zio_.rws.layer.{ReaderLive, Writer, WriterLive}


object Main extends Reint.Entry(Contender.ZIO % "Layer"):
  def prog(n: Int): (Vector[String], Vector[String]) =
    Writer.listen[Vector[String]](
      Query.listFruits.replicateZIO(n)
      .map(_.iterator.flatten.toVector)
    )
    .provide(
      ToLoggedHttp.layer,
      AccumulateLogMessages.layer,
      MockResponses.layer,
      WriterLive.layer[Vector[String]],
      ReaderLive.layer(Reint.Shared.RESPONSE),
    )
    .unsafeRunZio


  override def round1 = Reint.Shared.rep1(prog)
  override def round2 = Reint.Shared.rep2(prog)
  override def round3 = Reint.Shared.rep3(prog)
