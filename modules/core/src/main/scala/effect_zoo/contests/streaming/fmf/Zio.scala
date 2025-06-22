package effect_zoo.contests.streaming.fmf
import effect_zoo.contests.streaming.{Fmf, Contender}
import zio._
import zio.stream._
import effect_zoo.auxx.UnsafeRunZio._


object Zio extends Fmf.Entry(Contender.ZIO):
  override def round1 =
    ZStream
      .fromIterable(Fmf.theSeq)
      .filterZIO(x => ZIO.succeed(x % 2 == 0))
      .mapZIO(x => ZIO.succeed(x + 1))
      .runSum
      .unsafeRunZio
