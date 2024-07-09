package effect_zoo.contests
import effect_zoo.registry.{Contest1, Contender}


case object Sumh extends Contest1:
  override def description = "Sums a series of natural numbers the hard way. Uses multiple effects"

  override type Result1 = Either[String, (Int, Long, Int)]
  override def expected1 = Right((LIMIT, (LIMIT.toLong * (LIMIT + 1)) / 2, LIMIT + 1)).withLeft[String]

  val LIMIT = 100000

  override def enumEntries =
    import sumh._
    Vector(
      CatsCore,
      CatsCore_RWS,
      CatsMTL,
      CatsMTL_Desugared,
      CatsMTL_RWS,
      CatsMTL_RWS_Desugared,
      CatsIO,
      CatsIO_Desugared,
      CatsIO_RWS,
      CatsIO_RWS_Desugared,
      CatsEff,
      CatsEff_Desugared,
      Turbolift_Local,
      // Turbolift_Local_Bindless,
      Turbolift_Local_Desugared,
      Turbolift_Shared,
      Turbolift_Shared_Desugared,
      ZioCake,
      // ZioMono,
      ZioLayer,
      ZioEnv,
      ZioStateful,
      Zpure,
      Zpure_Desugared,
      // Kyo,
      Kyo_Desugared,
      Kyo_Direct,
      Unfunctional,
    )
