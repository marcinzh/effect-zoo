package effect_zoo.contests.streaming.crc
import effect_zoo.contests.streaming.{Crc, Contender}
import turbolift.!!
import turbolift.data.Chunk
import beam.{Stream, Source}
import Crc.Exports._


object Turbolift extends Crc.Entry(Contender.Turbolift):
  def consume1(stream: Stream[Byte, Any]): Long =
    stream
      .filter(_ < 128)
      .map(asciiToLower)
      .chunks
      .foldLeft(makeAccum())((acc, chunk) => acc ++ chunk.toArray)
      .run
      .getValue


  def consume2(stream: Stream[Byte, Any]): Long =
    stream
      .filter(_ < 128)
      .map(asciiToLower)
      .foldLeft(makeAccum())(_ :+ _)
      .run
      .getValue


  def prechunked(elementss: IndexedSeq[Array[Byte]]): Stream[Byte, Any] =
    Source.chunked: fx =>
      !!.impure(elementss.iterator).flatMap: it =>
        def loop: Unit !! fx.type =
          if it.hasNext then
            fx.emit(Chunk.from(it.next())) &&! loop
          else
            !!.unit
        loop

  def unchunked(elements: IndexedSeq[Byte]): Stream[Byte, Any] =
    Source.unchunked: fx =>
      !!.impure(elements.iterator).flatMap: it =>
        def loop: Unit !! fx.type =
          if it.hasNext then
            fx.emit(it.next()) &&! loop
          else
            !!.unit
        loop


  override def round1 = consume1(prechunked(theSeq1))
  override def round2 = consume1(prechunked(theSeq2))
  override def round3 = consume1(prechunked(theSeq3))
  override def round4 = consume1(prechunked(theSeq4))
  override def round5 = consume2(unchunked(theSeq5))
