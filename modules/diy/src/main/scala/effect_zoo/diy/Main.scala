package effect_zoo.diy
import effect_zoo.registry.Registry


object Main:
  def main(args: Array[String]) =
    val RxOption = "--(.+)".r
    args.toVector match
      case Vector() => printHelp()
      case Vector(RxOption(option)) => option match
        case "help" => printHelp()
        case "list" =>
          for reg <- Registry.benchmarkable do
            println(s"${reg.contest.nameLC}: ${reg.contest.description}.")
        case "all" => runBenchmarks(Registry.benchmarkable)
        case x =>
          println(s"Unknown option: `$x`")
          printHelp()
      case xs =>
        val rs =
          for
            x <- xs
            mr = Registry.benchmarkable.findByContestNameLC(x.toLowerCase)
            _ = if mr == None then println(s"Invalid benchmark name: `$x`")
            r <- mr
          yield r
        runBenchmarks(rs)

  def runBenchmarks(regs: Vector[Registry]) =
    for r <- regs do
      Benchmark.run(r)
      println

  def printHelp() =
    println("""
      |sbt diy/run --help               # prints this message
      |sbt diy/run --list               # prints names of available benchmarks
      |sbt diy/run --all                # runs all benchmarks
      |sbt diy/run <name1> <name2> ...  # runs specified benchmarks
    """.stripMargin.tail.init)
