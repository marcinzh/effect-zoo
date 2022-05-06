package effect_zoo.contests
import effect_zoo.registry.{Contest1, Contender}


object Cdown extends Contest1:
  override def name = "Cdown"
  override def description = "CountDown, the classic"
  override def description2 = "State effect used as a counter"
   
  override type Result1 = (Int, Int)
  override def expected1 = (0, 0)

  val LIMIT = 100000

  override def enumEntries =
    import cdown._
    Vector(
      CatsCore,
      CatsMTL,
      CatsEff,
      Turbolift,
      ZioCake,
      ZioMono,
      Zpure,
    )
