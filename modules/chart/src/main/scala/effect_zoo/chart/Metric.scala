package effect_zoo.chart
import scala.math._
import Metric_toplevel._


case class Metric private (value: Long, mscale: Int):
  assert(value >= 0)
  def scaleOrElseSpace: Char = scaleToSymbol(mscale)
  def scaleOrElse1: Char = scaleOrElseSpace match { case ' ' => '1'; case x => x }
  def scale: String = scaleOrElseSpace match { case ' ' => ""; case x => x.toString }

  def withoutScale(fracDigits: Int): String =
    val a: Long = abs(value)
    val i: Long = a / bigFigure
    if fracDigits > 0 then
      val d = (1 to fracDigits).foldLeft(bigFigure) { case (x, _) => x / 10L }
      val f = (a % bigFigure) / d
      s"${i}.${f.toString.padLeftTo(fracDigits, '0')}"
    else
      s"${i}"

  def withScale(fracDigits: Int): String = withoutScale(fracDigits) + scale
  def withPaddedScale(fracDigits: Int): String = withoutScale(fracDigits) + scaleOrElseSpace

  override def toString: String = withScale(2)


object Metric:
  def apply(x: Double): Metric = fromDouble(x)
  def fromDouble(x: Double): Metric =
    val scale = floor(log10(x) / 3.0)
    val tresh = pow(1000, scale)
    val big = (x / tresh) * bigFigure
    if big.isFinite && big > 0
    then Metric(big.toLong max bigFigure min biggerFigureM1, scale.toInt)
    else Metric(0L, 0)


private object Metric_toplevel:
  val bigFigure = 1000L * 1000L * 1000L * 1000L
  val biggerFigureM1 = 1000L * bigFigure - 1L

  val symbols = "yzafpnμm KMGTPEZY"
  val symbol1 = symbols.indexOf(' ')
  def scaleToSymbol(n: Int): Char = symbols.lift(n + symbol1).getOrElse('?')

  extension (thiz: String)
    def padLeftTo(size: Int, char: Char = ' '): String = thiz.reverse.padTo(size, char).reverse
