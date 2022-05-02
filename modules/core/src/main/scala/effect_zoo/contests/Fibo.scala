package effect_zoo.contests
import effect_zoo.registry.{Contest1, Contender}


object Fibo extends Contest1:
  override def name = "Fibo"
  override def description = "Overengineered Fibonacci function"
  override def description2 = "Uses Reader, Writer & State effects"

  override type Result1 = Either[String, (Int, Int, Int)]
  override def expected1 = Right((121393, 317809, 75025)).withLeft[String]

  val LIMIT = 100000

  override def enumEntries =
    import fibo._
    Vector(
      Unfunctional,
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
    )
