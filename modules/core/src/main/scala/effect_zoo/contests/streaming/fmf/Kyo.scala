package effect_zoo.contests.streaming.fmf
import effect_zoo.contests.streaming.{Fmf, Contender}
import kyo.*
import effect_zoo.auxx.UnsafeRunKyo._


object Kyo extends Fmf.Entry(Contender.Kyo):
  override def round1 =
    Stream
      .init(Fmf.theSeq)
      .filter(x => IO(x % 2 == 0))
      .map(x => IO(x + 1))
      .fold(0)(_ + _)
      .unsafeRunKyo
