package effect_zoo.contests
import effect_zoo.registry.{Contest5, Contender}

import effect_zoo.contests.mulst.Kyo

case object Mulst extends Contest5:
  override def description = "Multiple State effects used at the same time"
  override def roundNames = (for i <- 1 to 5 yield s"$i state${if i == 1 then "" else "s"}").toVector

  override type Result1 = Int
  override type Result2 = (Int, Int)
  override type Result3 = (Int, Int, Int)
  override type Result4 = (Int, Int, Int, Int)
  override type Result5 = (Int, Int, Int, Int, Int)
  override def expected1 = LIMIT * 11111
  override def expected2 = (LIMIT * 10101, LIMIT * 1010)
  override def expected3 = (LIMIT * 1001, LIMIT * 10010, LIMIT * 100)
  override def expected4 = (LIMIT * 10001, LIMIT * 10, LIMIT * 100, LIMIT * 1000)
  override def expected5 = (LIMIT * 1, LIMIT * 10, LIMIT * 100, LIMIT * 1000, LIMIT * 10000)

  val LIMIT = 10000

  override def enumEntries =
    import mulst._
    Vector(
      CatsCore,
      Turbolift,
      ZioEnv,
      ZioStateful,
      Kyo,
    )
