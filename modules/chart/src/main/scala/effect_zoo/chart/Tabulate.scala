package effect_zoo.chart


object Tabulate:
  type Alignment = Either[Int, Int]
  val defaultAlignment = Left(0)

  def tabulate(cells: Vector[String], alignment: Alignment = defaultAlignment): Vector[String] =
    val m =
      val m1 = alignment.fold(x => x, x => x)
      val m2 = cells.iterator.map(_.size).max
      if m1 == 0 then m2 else m1
    cells.map(padOrTruncate(_, m, alignment))

  private def padOrTruncate(text: String, maxWidth: Int, alignment: Alignment): String =
    val n = text.size
    if n < maxWidth then
      val padding = " " * (maxWidth - n)
      alignment match
        case Left(_) => text + padding
        case Right(_) => padding + text
    else
      text.take(maxWidth)

  def padRows(rows: Vector[Vector[String]], columnAlignments: Vector[Alignment] = Vector()): (Vector[Vector[String]], Vector[Alignment]) =
    val columnCount = columnAlignments.size max rows.iterator.map(_.size).max
    val columnAlignments2 = columnAlignments.padTo(columnCount, defaultAlignment)
    val rows2 = rows.map(_.padTo(columnCount, ""))
    (rows2, columnAlignments2)

  def tabulateRows(rows: Vector[Vector[String]], columnAlignments: Vector[Alignment] = Vector()): Vector[Vector[String]] =
    val (rows2, columnAlignments2) = padRows(rows, columnAlignments)
    val columns = for (column, columnAlignment) <- transpose(rows2).zip(columnAlignments2) yield
      tabulate(column, columnAlignment)
    transpose(columns)

  def transpose(rows: Vector[Vector[String]]): Vector[Vector[String]] =
    val columnCount = rows.headOption.map(_.size).getOrElse(0)
    (for columnIndex <- 0 until columnCount yield
      for row <- rows yield
        row(columnIndex)
    ).toVector
