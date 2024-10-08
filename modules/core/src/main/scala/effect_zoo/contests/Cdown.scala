package effect_zoo.contests
import effect_zoo.registry.{Contest1, Contender}


case object Cdown extends Contest1:
  override def description = "CountDown, the classic. State effect used as a counter"
   
  override type Result1 = (Int, Int)
  override def expected1 = (0, 0)

  val LIMIT = 10000

  override def enumEntries =
    import cdown._
    Vector(
      CatsCore,
      CatsMTL,
      CatsIO,
      CatsIORef,
      CatsEff,
      TurboliftLocal,
      TurboliftShared,
      ZioCake,
      ZioMono,
      ZioLayer,
      ZioEnv,
      ZioStateful,
      ZioRef,
      Zpure,
      Kyo,
    )
