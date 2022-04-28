package effect_zoo.bench
import effect_zoo.registry.{Contest, Contender}
import effect_zoo.chart.Chart


object Show:
  def apply(benchResults: Vector[(Contest, Vector[(Contender, Vector[Option[Double]])])]): Unit =
    for (contest, data) <- benchResults do
      val (labels, scores) = data.map { case (contender, x) => (contender.name, x) }.unzip
      val chart = Chart.full(contest.titles, labels, contest.roundNames, scores)
      chart.print()
      println()
