package effect_zoo.contests.streaming.map_fold_io
import effect_zoo.contests.streaming.{MapFoldIO, Contender}
import turbolift.effects.IO
import beam.Stream


object Turbolift extends MapFoldIO.Entry(Contender.Turbolift):
  override def round1 =
    Stream
      .from(MapFoldIO.theSeq)
      .filterEff(x => IO(x % 2 == 0))
      .mapEff(x => IO(x + 1))
      .foldLeft(0)(_ + _)
      .runIO.get
