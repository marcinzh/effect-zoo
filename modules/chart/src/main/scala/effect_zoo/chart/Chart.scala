package effect_zoo.chart


trait Chart:
  def setScore(rowIndex: Int, subIndex: Int, value: Option[Double]): Unit
  def setScore(rowIndex: Int, value: Option[Double]): Unit = setScore(rowIndex, 0, value)
  def setRunning(rowIndex: Int, subIndex: Int = 0): Unit
  def render: Vector[String]
  def print(): Unit = render.foreach(Console.println)
  def reprint(): Unit = { cursorUp(); print() }
  def cursorUp(): Unit


object Chart:
  def empty(titles: Vector[String], labels: Vector[String], subLabels: Vector[String], config: Config = Chart.Config()): Chart =
    ChartImpl(titles, labels, subLabels, config)

  def full(titles: Vector[String], labels: Vector[String], subLabels: Vector[String], scores: Vector[Vector[Option[Double]]], config: Config = Chart.Config()): Chart =
    val chart = empty(titles, labels, subLabels, config)
    for
      (ss, i) <- scores.zipWithIndex
      (s, j) <- ss.zipWithIndex
    do
      chart.setScore(i, j, s)
    chart

  final case class Config(
    barWidth: Int = 60,
    scoreFracDigits:Int = 2,
  )
