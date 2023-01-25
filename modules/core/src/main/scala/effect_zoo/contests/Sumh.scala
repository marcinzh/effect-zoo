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
      CatsMTL_RWS,
      CatsIO,
      CatsIO_RWS,
      CatsEff,
      Turbolift,
      TurboliftRef,
      ZioCake,
      ZioMono,
      Zpure,
      Unfunctional,
    )
