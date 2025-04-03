package effect_zoo.contests.streaming.map_fold_pure
import effect_zoo.contests.streaming.{MapFoldPure, Contender}
import turbolift.effects.IO
import beam.Stream


object Turbolift extends MapFoldPure.Entry(Contender.Turbolift):
  override def round1 =
    Stream
      .from(MapFoldPure.theSeq)
      .filter(x => x % 2 == 0)
      .map(x => x + 1)
      .foldLeft(0)(_ + _)
      .run
