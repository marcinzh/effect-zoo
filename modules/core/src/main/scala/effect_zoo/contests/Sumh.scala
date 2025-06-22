package effect_zoo.contests
import effect_zoo.registry.{Contest1, Contender}


case object Sumh extends Contest1:
  override def description = "Sums a series of natural numbers using Reader, Writer and State effects"

  override type Result1 = Either[String, (Int, Long, Int)]
  override def expected1 = Right((LIMIT, (LIMIT.toLong * (LIMIT + 1)) / 2, LIMIT + 1)).withLeft[String]

  val LIMIT = 10000

  override def enumEntries =
    import sumh._
    Vector(
      CatsCore,
      CatsCore_RWS,
      CatsMTL,
      CatsMTL_RWS,
      CatsIO,
      CatsIO_RWS,
      CatsEff,
      Turbolift_Local,
      Turbolift_Local_Bindless,
      Turbolift_Shared,
      Turbolift_Shared_Bindless,
      ZioCake,
      // ZioMono,
      ZioLayer,
      ZioEnv,
      ZioStateful,
      Zpure,
      Kyo,
      Kyo_Direct,
      Unfunctional,
    )
