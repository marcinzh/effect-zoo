package effect_zoo.contests.streaming.crc
import effect_zoo.contests.streaming.{Crc, Contender}
import zio._
import zio.stream._
import effect_zoo.auxx.UnsafeRunZio._
import Crc.Exports._


object Zio extends Crc.Entry(Contender.ZIO):
  def consume1(stream: ZStream[Any, Nothing, Byte]): Long =
    stream
      .filter(_ < 128)
      .map(asciiToLower)
      .chunks
      .runFold(makeAccum())((acc, chunk) => acc ++ chunk.toArray)
      .unsafeRunZio
      .getValue

  def consume2(stream: ZStream[Any, Nothing, Byte]): Long =
    stream
      .filter(_ < 128)
      .map(asciiToLower)
      .runFold(makeAccum())(_ :+ _)
      .unsafeRunZio
      .getValue


  def prechunked(elementss: IndexedSeq[Array[Byte]]): ZStream[Any, Nothing, Byte] =
    ZStream.fromChannel:
      ZChannel.succeed(elementss.iterator).flatMap: it =>
        def loop: ZChannel[Any, Any, Any, Any, Nothing, Chunk[Byte], Any] =
          if it.hasNext then
            ZChannel.write(Chunk.from(it.next())) *> loop
          else
            ZChannel.unit
        loop

  def unchunked(elements: IndexedSeq[Byte]): ZStream[Any, Nothing, Byte] =
    ZStream.fromChannel:
      ZChannel.succeed(elements.iterator).flatMap: it =>
        def loop: ZChannel[Any, Any, Any, Any, Nothing, Chunk[Byte], Any] =
          if it.hasNext then
            ZChannel.write(Chunk(it.next())) *> loop
          else
            ZChannel.unit
        loop


  override def round1 = consume1(prechunked(theSeq1))
  override def round2 = consume1(prechunked(theSeq2))
  override def round3 = consume1(prechunked(theSeq3))
  override def round4 = consume1(prechunked(theSeq4))
  override def round5 = consume2(unchunked(theSeq5))
