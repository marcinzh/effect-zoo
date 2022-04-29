package effect_zoo.diy
import effect_zoo.registry.Registry
import effect_zoo.chart.Chart


object Benchmark:
  def run(reg : Registry): Unit =
    implicit val config = Measure.Config()
    val chart = Chart.empty(
      titles = reg.contest.titles, 
      labels = reg.entries.map(_.contender.name),
      subLabels = reg.contest.roundNames,
    )
    chart.print()

    for (entry, entryIndex) <- reg.entries.zipWithIndex do
      for (round, roundIndex) <- entry.rounds.zipWithIndex do
        chart.setRunning(entryIndex, roundIndex)
        chart.reprint()
        val score = Measure(round.run())
        chart.setScore(entryIndex, roundIndex, Some(score))
        chart.reprint()
