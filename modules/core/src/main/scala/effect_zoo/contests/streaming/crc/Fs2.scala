package effect_zoo.contests.streaming.crc
import effect_zoo.contests.streaming.{Crc, Contender}
import fs2.*
import Crc.Exports._


object Fs2 extends Crc.Entry(Contender.Fs2):
  def consume1(stream: Stream[Pure, Byte]): Long =
    stream
      .filter(_ < 128)
      .map(asciiToLower)
      .chunks
      .compile
      .fold(makeAccum())((acc, chunk) => acc ++ chunk.toArray)
      .getValue

  def consume2(stream: Stream[Pure, Byte]): Long =
    stream
      .filter(_ < 128)
      .map(asciiToLower)
      .compile
      .fold(makeAccum())(_ :+ _)
      .getValue


  def prechunked[F[_]](elementss: IndexedSeq[Array[Byte]]): Stream[F, Byte] =
    Pull.pure(elementss.iterator).flatMap: it =>
      def loop: Pull[F, Byte, Unit] =
        if it.hasNext then
          Pull.output(Chunk.from(it.next())) >> loop
        else
          Pull.done
      loop
    .stream

  def unchunked[F[_]](elements: IndexedSeq[Byte]): Stream[F, Byte] =
    Pull.pure(elements.iterator).flatMap: it =>
      def loop: Pull[F, Byte, Unit] =
        if it.hasNext then
          Pull.output1(it.next()) >> loop
        else
          Pull.done
      loop
    .stream


  override def round1 = consume1(prechunked(theSeq1))
  override def round2 = consume1(prechunked(theSeq2))
  override def round3 = consume1(prechunked(theSeq3))
  override def round4 = consume1(prechunked(theSeq4))
  override def round5 = consume2(unchunked(theSeq5))
