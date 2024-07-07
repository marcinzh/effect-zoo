package effect_zoo.bench
import java.util.concurrent.TimeUnit
import scala.jdk.CollectionConverters._
import org.openjdk.jmh.runner.options.TimeValue
import org.openjdk.jmh.runner.options.OptionsBuilder
import org.openjdk.jmh.results.RunResult
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.CommandLineOptions
import effect_zoo.chart.Chart


object Main:
  def main(args: Array[String]): Unit =
    val runner =
      if args == Array("devel") then
        val opts = new OptionsBuilder()
          .forks(1)
          .warmupIterations(3)
          .measurementIterations(5)
          .warmupTime(TimeValue(3, TimeUnit.MILLISECONDS))
          .measurementTime(TimeValue(5, TimeUnit.MILLISECONDS))
          .build()
        new Runner(opts)
      else
        val opts = new CommandLineOptions(args*)
        new Runner(opts)

    val results = runner.run()

    val results2 = for r <- results.asScala.toVector yield
      val rr = r.getAggregatedResult.getPrimaryResult
      (r.getParams.getBenchmark(), rr.getStatistics.getMean)

    Show(Parse(results2))
