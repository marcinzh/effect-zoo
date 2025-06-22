package effect_zoo.contests.streaming.crc
import effect_zoo.contests.streaming.{Crc, Contender}
import kyo.*
import kyo.{Kyo => KYO}
import Crc.Exports._


object Kyo extends Crc.Entry(Contender.Kyo):
  def consume1(stream: Stream[Byte, Any]): Long =
    stream
      .filter(_ < 128)
      .map(asciiToLower)
      .mapChunk(chunk => Chunk(chunk))
      .fold(makeAccum())((acc, chunk) => acc ++ chunk.iterator.toArray)
      .eval
      .getValue

  def consume2(stream: Stream[Byte, Any]): Long =
    stream
      .filter(_ < 128)
      .map(asciiToLower)
      .fold(makeAccum())(_ :+ _)
      .eval
      .getValue


  def prechunked(elementss: IndexedSeq[Array[Byte]]): Stream[Byte, Any] =
    Stream(KYO.foreachDiscard(elementss)(array => Emit.value(Chunk.from(array))))

  def unchunked(elements: IndexedSeq[Byte]): Stream[Byte, Any] =
    Stream(KYO.foreachDiscard(elements)(a => Emit.value(Chunk(a))))


  override def round1 = consume1(prechunked(theSeq1))
  override def round2 = consume1(prechunked(theSeq2))
  override def round3 = consume1(prechunked(theSeq3))
  override def round4 = consume1(prechunked(theSeq4))
  override def round5 = consume2(unchunked(theSeq5))
