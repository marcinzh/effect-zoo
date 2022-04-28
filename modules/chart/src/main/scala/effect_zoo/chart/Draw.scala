package effect_zoo.chart
import scala.math._


class Draw(barWidth: Int, columnWidths: Vector[Int]):
  assert(barWidth % 10 == 0)
  private val gridWidth = columnWidths.sum + (columnWidths.size - 1) * 3 + 0

  private val blocks = " \u258F\u258E\u258D\u258C\u258B\u258A\u2589\u2588"
  private val fullBlock = blocks.last.toString

  def bar(value: Double, colorIndex: Int): String =
    val f = barWidth * (value min 1.0 max 0.0)
    val fullBlockCount = floor(f).toInt
    val fractBlockIndex = round((f - floor(f)) * 8).toInt
    val fractBlock = if fractBlockIndex == 0 then "" else blocks(fractBlockIndex)
    val bar = fullBlock * fullBlockCount + fractBlock
    val scales2 = scales.drop(bar.size)
    Colored.border("▕") +
    Colored.palette(colorIndex)(bar) + 
    Colored.scale(scales2) +
    Colored.border("▏")

  def emptyBar: String = bar(0, 0)

  private val scales = ("▏" + (" " * (barWidth / 10 - 1))) * 10

  private def hBorder(ch: Char) =
    Colored.border.spaces(gridWidth + 1) +
    Colored.border(" " + (ch.toString * barWidth) + " ")

  def topBorder = hBorder('▁')
  def bottomBorder = hBorder('▔')

  def gridRow(texts: Vector[String]): String =
    texts.map(Colored.text(_))
    .mkString(Colored.border.spaces(1), Colored.border(" │ "), "")

  def separatorRow: String =
    Colored.border(columnWidths.map("─" * _).mkString(" ", "─┼─", "")) + emptyBar

  def headers(texts: Vector[String]): Vector[String] =
    val Vector(a, b, c) = texts.take(3).padTo(3, "")
    Vector(s"$a: $b", c)
    .map(" " + _)
    .map(_.padTo(gridWidth + barWidth + 3, ' '))
    .zipWithIndex.map { case (x, 0) => s"${Console.BOLD}$x" ; case (x, _) => x }
    .map(Colored.text(_))


object Draw:
  def cursorUp(n: Int): String = s"\u001b[${n + 4}A"
