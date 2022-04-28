package effect_zoo.bench
import scala.util.chaining._
import effect_zoo.registry.{Contest, Contender}


object Parse:
  def apply(benchResults: Vector[(String, Double)]): Vector[(Contest, Vector[(Contender, Vector[Option[Double]])])] =
    groupScores(benchResults)

  private val PrefixRx = """effect_zoo\.bench\.contests\.(\w+)\.(.+)""".r
  private val RoundfulRx = """(\w+)__(\d+)""".r
  private val RoundlessRx = """(\w+)""".r
  private def parseBenchmarkName(text: String): (Contest, Contender, Int) =
    val PrefixRx(contestName, rest) = text
    val (contenderName, roundIndex) = rest match
      case RoundfulRx(x, y) => (x, y.toInt)
      case RoundlessRx(x) => (x, 0)
    (Contest.fromString(contestName), Contender.fromString(contenderName), roundIndex)

  private def groupScores(benchResults: Vector[(String, Double)]) =
    val tuples =
      (for (benchName, score) <- benchResults yield
        val (contestName, contender, roundIndex) = parseBenchmarkName(benchName)
        (contestName, (contender, (roundIndex, score)))
      )
    groupByContest(tuples)

  private def groupByContest(tuples: Vector[(Contest, (Contender, (Int, Double)))]): Vector[(Contest, Vector[(Contender, Vector[Option[Double]])])] =
    tuples.groupMap(_._1)(_._2)
    .pipe(reorderContests)
    .map { case (contest, rest) => (contest, groupByContender(rest, contest.roundCount)) }

  private def groupByContender(tuples: Vector[(Contender, (Int, Double))], roundCount: Int): Vector[(Contender, Vector[Option[Double]])] =
    tuples.groupMap(_._1)(_._2)
    .pipe(reorderContenders)
    .map { case (c, rest) => (c, groupByRound(rest, roundCount)) }

  private def groupByRound(tuples: Vector[(Int, Double)], roundCount: Int): Vector[Option[Double]] =
    tuples.toMap
    .pipe(reorderRounds(_, roundCount))

  private def reorderContests[T](m: Map[Contest, T]): Vector[(Contest, T)] =
    Contest.all.flatMap(c => m.get(c).map((c, _)))

  private def reorderContenders[T](m: Map[Contender, T]): Vector[(Contender, T)] =
    val mm: Map[Contender, Vector[(Contender, T)]] =
      m.toVector.groupBy(_._1.plain)
      .view.mapValues(_.sortBy(_._1.tag)).toMap
    Contender.all.flatMap(c => mm.get(c)).flatten

  private def reorderRounds[T](m: Map[Int, Double], n: Int): Vector[Option[Double]] =
    for i <- (0 until n).toVector yield m.get(i)
