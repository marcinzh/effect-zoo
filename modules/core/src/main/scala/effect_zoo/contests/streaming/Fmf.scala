package effect_zoo.contests.streaming
import effect_zoo.registry.{Contest1, Contender}


case object Fmf extends Contest1:
  override def description = "Effectful processing of a stream: filter, map and fold"
   
  override type Result1 = Int
  override def expected1 = 25000000

  val theSeq: IndexedSeq[Int] = IArray.from(0 until 10000)

  override def enumEntries =
    import fmf._
    Vector(
      Fs2,
      Zio,
      Kyo,
      Turbolift,
    )
