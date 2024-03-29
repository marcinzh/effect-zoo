package effect_zoo.meta


object Template:
  private val template = """
    |/*******************************/
    |/* Generated by `sbt meta/run` */
    |/*******************************/
    |package effect_zoo.bench.contests
    |import java.util.concurrent.TimeUnit
    |import org.openjdk.jmh.annotations._
    |import effect_zoo.registry.Registry
    |
    |@State(Scope.Thread)
    |@BenchmarkMode(Array(Mode.Throughput))
    |@OutputTimeUnit(TimeUnit.SECONDS)
    |@Fork(jvmArgs = Array("-Dcats.effect.tracing.mode=DISABLED", "-Xms2g", "-Xmx2g"))
    |class CONTEST_NAME {
    |  val reg = Registry.findByContestName("CONTEST_NAME")
    |
    |CLASS_BODY
    |}
  """.stripMargin.split('\n').toVector.tail.init

  def apply(name: String, body: Vector[String]): String =
    val lines = template.map(_.replace("CONTEST_NAME", name))
    val (linesBefore, _ +: linesAfter) = lines.span(_ != "CLASS_BODY"): @unchecked
    val content2 = body.map("  " + _)
    val lines2 = linesBefore ++ content2 ++ linesAfter
    lines2.mkString("", "\n", "\n")
