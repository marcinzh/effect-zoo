package effect_zoo.chart


class Colored(fgc: Int, bgc: Int):
  def apply(text: String): String = 
    if text.isEmpty
    then ""
    else Colored.bg(bgc) + Colored.fg(fgc) + text + Console.RESET

  def spaces(n: Int) = apply(" " * n)


object Colored:
  private val isOk = !".*windows.*".r.matches(System.getProperty("os.name").toLowerCase)
  private def ifOk(s: String) = if isOk then s else ""

  def palette(i: Int) = colors(i % colors.size)

  private def fg(n: Int) = ifOk("\u001b[38;2;" + rgb(n))
  private def bg(n: Int) = ifOk("\u001b[48;2;" + rgb(n))
  private def rgb(n: Int) = ifOk(s"${(n >> 16) & 255};${(n >> 8) & 255};${n & 255}m")

  private val textFg = 0
  private val gridFg = 0xc0c0c0
  private val textBg = 0xf0f0f0
  private val barBg = 0xffffff
  private val borderFg = 0xc0c0c0
  private val scaleFg = 0xe0e0e0

  val text = Colored(textFg, textBg)
  val grid = Colored(gridFg, textBg)
  val border = Colored(borderFg, textBg)
  val scale = Colored(scaleFg, barBg)

  private lazy val colors = Vector(
    0xe07474,
    0x50c4c4,
    0xd0d050,
    0x58a458,
    0xa860d0,
    0xd09850,
    0x5090d0,
    0xd090d0,
    0x6060d0,
    0x90d050,
    0xd058ac,
    0xa4a450,
  ).map(Colored(_, barBg))
