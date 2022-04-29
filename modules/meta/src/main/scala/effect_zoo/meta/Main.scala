package effect_zoo.meta
import better.files._
import File._
import effect_zoo.registry.{Contest, Contender, Registry}


object Niam:
  private val targetDir = "./modules/bench/src/main/scala/effect_zoo/bench/contests"

  @main def main: Unit =
    for reg <- Registry.benchmarkable do
      val content = makeContent(reg)
      val file = File(targetDir)./(s"${reg.contest.name}.scala")
      println(s"Writing generated code to `${file}`")
      file.writeText(content)

  def makeContent(reg: Registry): String =
    Template(reg.contest.name, makeBody(reg))

  def makeBody(reg: Registry): Vector[String] =
    val (as, bs) =
      (for
        entry <- reg.entries
        name = entry.contender.name
        (round, index) <- entry.rounds.zipWithIndex
        roundSuffix = if entry.rounds.size > 1 then s"__$index" else ""
        runIdentifier = s"${name}${roundSuffix}__run"
        line1 = s"val ${runIdentifier} = reg.findRound(\"${name}\", ${index}).run"
        line2 = s"@Benchmark def ${name}${roundSuffix} = ${runIdentifier}()"
      yield (line1, line2))
      .unzip
    as ++ Vector("") ++ bs
