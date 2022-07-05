package effect_zoo.contests
import effect_zoo.registry.{Contest3, Contender}


case object Reint extends Contest3:
  override def description = "Reinterpretation (handling effects by translating them into other effects)"
  override def description2 = "Demonstrates FP way of \"program to an interface\""
  override def roundNames = Vector(Shared.REPS1, Shared.REPS2, Shared.REPS3).map(n => s"$n queries/batch")

  override type Result1 = (Vector[String], Vector[String])
  override type Result2 = Result1
  override type Result3 = Result1
  override lazy val expected1 = expected(Shared.REPS1)
  override lazy val expected2 = expected(Shared.REPS2)
  override lazy val expected3 = expected(Shared.REPS3)

  private def expected(n: Int) = (
    Vector.fill(n)(Shared.RESPONSE.split('\n').toVector).flatten,
    Vector.fill(n)(Shared.MESSAGE),
  )


  object Shared:
    val RESPONSE = "Apple,Banana,Orange,Strawberry".split(',').mkString("\n")
    val MESSAGE = "Fetching fruits."
    val URL = "/fruits"

    def REPS1 = 100
    def REPS2 = 1000
    def REPS3 = 10000

    def rep1[A](f: Int => A): A = rep(REPS1)(f)
    def rep2[A](f: Int => A): A = rep(REPS2)(f)
    def rep3[A](f: Int => A): A = rep(REPS3)(f)

    private def rep[A](n: Int): (Int => A) => A =
      (f: Int => A) =>
        @annotation.tailrec def loop(k: Int): A =
          val a = f(n)
          if k <= 1
          then a
          else loop(k - 1)
        loop(REPS3 / n)


  override def enumEntries =
    import reint._
    Vector(
      cats_mtl.Main,
      cats_eff.Main,
      turbolift_.Main,
      zio_mono.Main,
    )
