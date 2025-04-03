package effect_zoo.contests.streaming.map_fold_pure
import effect_zoo.contests.streaming.{MapFoldPure, Contender}


object StdLib extends MapFoldPure.Entry(Contender.StdLib):
  override def round1 =
    LazyList
      .from(MapFoldPure.theSeq)
      .filter(x => x % 2 == 0)
      .map(x => x + 1)
      .foldLeft(0)(_ + _)
