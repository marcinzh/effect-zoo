package effect_zoo.contests.streaming
import effect_zoo.registry.{Contest1, Contender}


case object MapFoldIO extends Contest1:
  override def description = "Stream: MapFoldIO"
   
  override type Result1 = Int
  override def expected1 = 25000000

  val theSeq: Seq[Int] = IArray.from(0 until 10000)

  override def enumEntries =
    import map_fold_io._
    Vector(
      Fs2,
      Zio,
      Kyo,
      Turbolift,
      // StdLib,
    )
