package effect_zoo.contests.streaming.map_fold_io
import effect_zoo.contests.streaming.{MapFoldIO, Contender}
import kyo.*
import effect_zoo.auxx.UnsafeRunKyo._


object Kyo extends MapFoldIO.Entry(Contender.Kyo):
  override def round1 =
    Stream
      .init(MapFoldIO.theSeq)
      .filter(x => IO(x % 2 == 0))
      .map(x => IO(x + 1))
      .fold(0)(_ + _)
      .unsafeRunKyo
