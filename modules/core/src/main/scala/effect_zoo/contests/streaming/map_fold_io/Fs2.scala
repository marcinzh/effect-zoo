package effect_zoo.contests.streaming.map_fold_io
import effect_zoo.contests.streaming.{MapFoldIO, Contender}
import cats.effect.*
import fs2.*
import cats.effect.unsafe.implicits.global


object Fs2 extends MapFoldIO.Entry(Contender.Fs2):
  override def round1 =
    Stream
      .emits(MapFoldIO.theSeq)
      .evalFilter(x => IO(x % 2 == 0))
      .evalMap(x => IO(x + 1))
      .compile
      .fold(0)(_ + _)
      .unsafeRunSync()
