package effect_zoo.contests.streaming.map_fold_pure
import effect_zoo.contests.streaming.{MapFoldPure, Contender}
import kyo.*


object Kyo extends MapFoldPure.Entry(Contender.Kyo):
  override def round1 =
    Stream
      .init(MapFoldPure.theSeq)
      .filter(x => x % 2 == 0)
      .map(x => x + 1)
      .fold(0)(_ + _)
      .eval
