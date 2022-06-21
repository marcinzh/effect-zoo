package effect_zoo.contests
import effect_zoo.registry.{Contest1, Contender}


object Sumh extends Contest1:
  override def name = "Sumh"
  override def description = "Sums a series of natural numbers the hard way"
  override def description2 = "Uses Reader, Writer & State effects"

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
      CatsEff,
      Turbolift,
      Turbolift_RWS,
      ZioCake,
      ZioMono,
      Zpure,
      Unfunctional,
    )
