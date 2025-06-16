package effect_zoo.contests.streaming.map_fold_io
import effect_zoo.contests.streaming.{MapFoldIO, Contender}
import zio._
import zio.stream._
import effect_zoo.auxx.UnsafeRunZio._


object Zio extends MapFoldIO.Entry(Contender.ZIO):
  override def round1 =
    ZStream
      .fromIterable(MapFoldIO.theSeq)
      .filterZIO(x => ZIO.succeed(x % 2 == 0))
      .mapZIO(x => ZIO.succeed(x + 1))
      .runSum
      .unsafeRunZio
