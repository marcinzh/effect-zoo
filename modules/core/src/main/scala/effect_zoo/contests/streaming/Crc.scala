package effect_zoo.contests.streaming
import java.util.zip.CRC32
import effect_zoo.registry.{Contest5, Contender}
import Crc.CRC32Syntax._


case object Crc extends Contest5:
  override def description = "Computes CRC32 of a stream of bytes, one Chunk at a time"
  override def roundNames = Vector(
    s"Chunk size = $chunkSize1",
    s"Chunk size = $chunkSize2",
    s"Chunk size = $chunkSize3",
    s"Chunk size = $chunkSize4",
    "Unchunked",
  )
   
  override type Result1 = Long
  override type Result2 = Long
  override type Result3 = Long
  override type Result4 = Long
  override type Result5 = Long
  override def expected1 = expected
  override def expected2 = expected
  override def expected3 = expected
  override def expected4 = expected
  override def expected5 = expected

  def chunkSize1 = 2048 - 16
  def chunkSize2 = 512 - 16
  def chunkSize3 = 128 - 16
  def chunkSize4 = 64 - 16
  def byteCount = 20000
  val bytes = scala.util.Random(13370042).nextBytes(byteCount)
  val expected = makeAccum().++(bytes.filter(_ < 128).map(asciiToLower)).getValue()

  object CRC32Syntax:
    def asciiToLower(b: Byte): Byte = if 65 <= b && b <= 90 then (b | 0x20).toByte else b
    def makeAccum() = new CRC32
    extension (acc: CRC32)
      def ++ (bytes: Array[Byte]): CRC32 = { acc.update(bytes); acc }
      def :+ (byte: Byte): CRC32 = { acc.update(byte); acc }

  object Exports:
    export CRC32Syntax._
    val theSeq1: IndexedSeq[Array[Byte]] = bytes.grouped(chunkSize1).map(_.toArray).toIndexedSeq
    val theSeq2: IndexedSeq[Array[Byte]] = bytes.grouped(chunkSize2).map(_.toArray).toIndexedSeq
    val theSeq3: IndexedSeq[Array[Byte]] = bytes.grouped(chunkSize3).map(_.toArray).toIndexedSeq
    val theSeq4: IndexedSeq[Array[Byte]] = bytes.grouped(chunkSize4).map(_.toArray).toIndexedSeq
    val theSeq5: IndexedSeq[Byte] = bytes.toIndexedSeq

  override def enumEntries =
    import crc._
    Vector(
      Fs2,
      Zio,
      Kyo,
      Turbolift,
    )
