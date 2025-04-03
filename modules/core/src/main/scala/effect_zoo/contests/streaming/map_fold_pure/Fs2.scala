package effect_zoo.contests.streaming.map_fold_pure
import effect_zoo.contests.streaming.{MapFoldPure, Contender}
import cats.effect.*
import fs2.*


object Fs2 extends MapFoldPure.Entry(Contender.Fs2):
  override def round1 =
    Stream
      .emits(MapFoldPure.theSeq)
      .filter(x => x % 2 == 0)
      .map(x => x + 1)
      .compile
      .fold(0)(_ + _)
