package effect_zoo.registry


trait Contest extends Product:
  final def name: String = productPrefix
  def description: String
  def roundNames: Vector[String] = Vector()
  def benchmarkable: Boolean = true

  final val nameLC = name.toLowerCase
  final def titles = Vector(nameLC, description)
  final def isFlat = roundNames.isEmpty
  final def roundCount = if isFlat then 1 else roundNames.size

  private[registry] def register: Registry


object Contest:
  val all: Vector[Contest] = 
    import effect_zoo.contests._
    import effect_zoo.contests.streaming.StreamingContests
    Vector(
      Cdown,
      Sumh,
      Mulst,
      Reint,
    ) ++ StreamingContests.enumContests

  def fromString(text: String): Contest =
    all.find(_.name == text).get
