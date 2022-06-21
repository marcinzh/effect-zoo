package effect_zoo.registry


trait Contest:
  def name: String
  def description: String
  def description2: String
  def roundNames: Vector[String] = Vector()
  def benchmarkable: Boolean = true

  final val nameLC = name.toLowerCase
  final def titles = Vector(name, description, description2)
  final def isFlat = roundNames.isEmpty
  final def roundCount = if isFlat then 1 else roundNames.size

  private[registry] def register: Registry


object Contest:
  val all: Vector[Contest] = 
    import effect_zoo.contests._
    Vector(
      Cdown,
      Sumh,
      Mulst,
      Reint,
    )

  def fromString(text: String): Contest =
    all.find(_.name == text).get
