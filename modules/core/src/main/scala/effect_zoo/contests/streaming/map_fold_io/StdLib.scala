package effect_zoo.contests.streaming.map_fold_io
import effect_zoo.contests.streaming.{MapFoldIO, Contender}


object StdLib extends MapFoldIO.Entry(Contender.StdLib):
  override def round1 =
    LazyList
      .from(MapFoldIO.theSeq)
      .filter(x => x % 2 == 0)
      .map(x => x + 1)
      .foldLeft(0)(_ + _)
