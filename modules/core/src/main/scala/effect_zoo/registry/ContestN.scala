package effect_zoo.registry
import effect_zoo.registry.{Entry => RegisteredEntry}


trait Contest1 extends ContestN.Contest1:
  abstract class Entry(override val contender: Contender) extends Entry1

trait Contest2 extends ContestN.Contest2:
  abstract class Entry(override val contender: Contender) extends Entry2

trait Contest3 extends ContestN.Contest3:
  abstract class Entry(override val contender: Contender) extends Entry3

trait Contest4 extends ContestN.Contest4:
  abstract class Entry(override val contender: Contender) extends Entry4

trait Contest5 extends ContestN.Contest5:
  abstract class Entry(override val contender: Contender) extends Entry5


private object ContestN:
  sealed trait Contest0 extends Contest:
    private[registry] def enumEntries: Vector[Entry0]
    private[registry] final def makeRound[T](a: => T, b: T): Round = Round(() => a, b)
    private[registry] final override def register: Registry = Registry(this, enumEntries.map(_.register))
    sealed trait Entry0:
      def contender: Contender
      def enumRounds: Vector[Round]
      private[registry] final def register: RegisteredEntry = RegisteredEntry(contender, enumRounds)

  sealed trait Contest1 extends Contest0:
    type Result1
    def expected1: Result1
    sealed trait Entry1 extends Entry0:
      def round1: Result1
      override def enumRounds = Vector(makeRound(round1, expected1))

  sealed trait Contest2 extends Contest1:
    type Result2
    def expected2: Result2
    sealed trait Entry2 extends Entry1:
      def round2: Result2
      override def enumRounds = super.enumRounds :+ makeRound(round2, expected2)

  sealed trait Contest3 extends Contest2:
    type Result3
    def expected3: Result3
    sealed trait Entry3 extends Entry2:
      def round3: Result3
      override def enumRounds = super.enumRounds :+ makeRound(round3, expected3)

  sealed trait Contest4 extends Contest3:
    type Result4
    def expected4: Result4
    sealed trait Entry4 extends Entry3:
      def round4: Result4
      override def enumRounds = super.enumRounds :+ makeRound(round4, expected4)

  sealed trait Contest5 extends Contest4:
    type Result5
    def expected5: Result5
    sealed trait Entry5 extends Entry4:
      def round5: Result5
      override def enumRounds = super.enumRounds :+ makeRound(round5, expected5)
