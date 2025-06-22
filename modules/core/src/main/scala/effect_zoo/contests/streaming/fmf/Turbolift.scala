package effect_zoo.contests.streaming.fmf
import effect_zoo.contests.streaming.{Fmf, Contender}
import turbolift.effects.IO
import beam.Stream


object Turbolift extends Fmf.Entry(Contender.Turbolift):
  override def round1 =
    Stream
      .from(Fmf.theSeq)
      .filterEff(x => IO(x % 2 == 0))
      .mapEff(x => IO(x + 1))
      .foldLeft(0)(_ + _)
      .runIO.get
