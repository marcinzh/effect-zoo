package effect_zoo.registry


final case class Registry(contest: Contest, entries: Vector[Entry]):
  assert(entries.forall(e => e.rounds.size == contest.roundCount))
  def filterEntries(f: Entry => Boolean) = copy(entries = entries.filter(f))
  def findEntry(contenderName: String): Entry = entries.find(_.contender.name == contenderName).get
  def findRound(contenderName: String, roundIndex: Int): Round = findEntry(contenderName).rounds(roundIndex)
  def findRound(contenderName: String): Round = findEntry(contenderName).rounds(0)

final case class Entry(contender: Contender, rounds: Vector[Round])

final case class Round(run: () => Any, expected: Any)


object Registry:
  val all: Vector[Registry] = Contest.all.map(_.register)
  val functional = all.excludeContenders(Contender.Unfunctional)
  val benchmarkable = functional.filter(_.contest.benchmarkable)
  def findByContestName(name: String): Registry = all.find(_.contest.name == name).get


  extension (thiz: Vector[Registry])
    def filterEntries(f: Entry => Boolean): Vector[Registry] =
      thiz.map(reg => reg.copy(entries = reg.entries.filter(f)))
      .filter(_.entries.nonEmpty)
    def selectContenders(contenders: Seq[Contender], xor: Boolean): Vector[Registry] = filterEntries(e => xor ^ contenders.contains(e.contender.plain))
    def includeContenders(contenders: Contender*): Vector[Registry] = selectContenders(contenders, false)
    def excludeContenders(contenders: Contender*): Vector[Registry] = selectContenders(contenders, true)
    def findByContestNameLC(name: String): Option[Registry] = thiz.find(_.contest.nameLC == name)
