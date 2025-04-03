package effect_zoo.registry


sealed trait Contender:
  def plain: Contender.Plain
  def name: String
  def tag: String
  final def %(s: String): Contender = new Contender.Tagged(plain, s)


object Contender:
  sealed trait Plain extends Contender with Product:
    final override def plain: Plain = this
    final override def name: String = productPrefix
    final override def tag: String = ""

  private final case class Tagged(override val plain: Plain, override val tag: String) extends Contender:
    assert("""\w+""".r.matches(tag))
    override def name = s"${plain.name}_${tag}"

  lazy val byName = all.map(c => c.name -> c).toMap

  def fromString(text: String): Contender =
    text match
      case RxWithMinor(a, b) => byName(a) % b
      case a => byName(a)

  private val RxWithMinor = """([^_]+)_(\w+)""".r

  case object Unfunctional extends Plain
  case object CatsCore extends Plain
  case object CatsMTL extends Plain
  case object CatsIO extends Plain
  case object CatsEff extends Plain
  case object Fs2 extends Plain
  case object Turbolift extends Plain
  case object ZIO extends Plain
  case object ZPure extends Plain
  case object Kyo extends Plain
  case object StdLib extends Plain { override def toString = "LazyList" }

  val all = Vector(
    Unfunctional,
    CatsCore,
    CatsMTL,
    CatsIO,
    CatsEff,
    Fs2,
    ZIO,
    Kyo,
    ZPure,
    Turbolift,
    StdLib,
  )