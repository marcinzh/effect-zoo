package effect_zoo.chart
import ChartImpl._


private[chart] object ChartImpl:
  def apply(titles: Vector[String], labels: Vector[String], subLabels: Vector[String], config: Chart.Config, dummy: Boolean = false) =
    new ChartImpl(titles, labels.map(Row(_, subLabels.size)), subLabels, config)

  class Row(val label: String, size: Int):
    val scores: Array[Either[Missing, Double]] = Array.fill(size max 1)(Left(Missing.Empty))

  enum Missing:
    case Empty, Running, Failed


private[chart] class ChartImpl(titles: Vector[String], rows: Vector[Row], subLabels: Vector[String], config: Chart.Config) extends Chart:
  def isFlat = subLabels.isEmpty
  def depth = subLabels.size max 1
  def scoreWidth = 3 +1 + config.scoreFracDigits + 1 // int '.' frac unit

  override def setScore(rowIndex: Int, subIndex: Int, value: Option[Double]): Unit =
    rows(rowIndex).scores(subIndex) = value.fold(Left(Missing.Failed))(Right(_))

  override def setRunning(rowIndex: Int, subIndex: Int): Unit =
    rows(rowIndex).scores(subIndex) = Left(Missing.Running)

  override def cursorUp(): Unit =
    val a = rows.size
    val b = if isFlat then a else (depth + 1) * a - 1
    Console.print(Draw.cursorUp(b))

  private def renderScore(score: Either[Missing, Double]): String =
    score match
      case Right(x) => Metric(x).withPaddedScale(config.scoreFracDigits)
      case Left(Missing.Failed) => "DNF "
      case Left(Missing.Running) => "... "
      case _ => ""

  private def renderTexts1D: Vector[Vector[String]] =
    val unaligned =
      for row <- rows yield
        val score = renderScore(row.scores.head)
        Vector(row.label, score)
    Tabulate.tabulateRows(unaligned, Vector(Left(0), Right(scoreWidth)))


  private def renderTexts2D: Vector[Vector[String]] =
    val unaligned =
      for
        row <- rows
        (score, subLabel) <- row.scores.toVector.zip(subLabels)
        score2 = renderScore(score)
      yield Vector(row.label, subLabel, score2)
    Tabulate.tabulateRows(unaligned, Vector(Left(0), Left(0), Right(scoreWidth)))


  override def render: Vector[String] =
    if rows.isEmpty then Vector() else
      val (textss, columnWidths): (Vector[Vector[String]], Vector[Int]) =
        val xss: Vector[Vector[String]] = if isFlat then renderTexts1D else renderTexts2D
        (xss, xss.head.map(_.size))

      val draw = Draw(config.barWidth, columnWidths)
      val texts = textss.map(draw.gridRow)
        
      val bars =
        val maxScore = (for { r <- rows; s <- r.scores } yield s.getOrElse(0.0)).max
        for
          (row, rowIndex) <- rows.zipWithIndex
          (score, scoreIndex) <- row.scores.zipWithIndex
        yield
          val colorIndex = if isFlat then rowIndex else scoreIndex
          val bar = draw.bar(score.getOrElse(0.0) / maxScore, colorIndex)
          bar
          
      val lines = for (text, bar) <- texts.zip(bars) yield text + bar
      
      val lines2 = if isFlat then lines else
        lines.grouped(depth).toVector match
          case Vector(xs) => xs
          case xss :+ xs => xss.flatMap(_ :+ draw.separatorRow) ++ xs
          case _ => ???

      draw.headers(titles) ++ (draw.topBorder +: lines2 :+ draw.bottomBorder)
