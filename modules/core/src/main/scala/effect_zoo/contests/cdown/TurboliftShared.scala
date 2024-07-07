package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import turbolift.!!
import turbolift.effects.State


object TurboliftShared extends Cdown.Entry(Contender.Turbolift % "Shared"):
  import TurboliftLocal.{program, MyState}

  override def round1 =
    program
    .handleWith(MyState.handlers.shared(Cdown.LIMIT))
    .runIO.get
