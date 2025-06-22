package effect_zoo.contests.streaming.fmf
import effect_zoo.contests.streaming.{Fmf, Contender}
import cats.effect.*
import fs2.*
import cats.effect.unsafe.implicits.global


object Fs2 extends Fmf.Entry(Contender.Fs2):
  override def round1 =
    Stream
      .emits(Fmf.theSeq)
      .evalFilter(x => IO(x % 2 == 0))
      .evalMap(x => IO(x + 1))
      .compile
      .fold(0)(_ + _)
      .unsafeRunSync()
