package effect_zoo.contests.streaming.map_fold_pure
import effect_zoo.contests.streaming.{MapFoldPure, Contender}
import zio._
import zio.stream._
import effect_zoo.auxx.UnsafeRunZio._


object Zio extends MapFoldPure.Entry(Contender.ZIO):
  override def round1 =
    ZStream
      .fromIterable(MapFoldPure.theSeq)
      .filter(x => x % 2 == 0)
      .map(x => x + 1)
      .runSum
      .unsafeRunZio
