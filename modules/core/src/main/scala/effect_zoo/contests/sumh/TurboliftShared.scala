package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import turbolift.!!
import turbolift.effects.{Reader, Writer, State, Error}


object TurboliftShared extends Sumh.Entry(Contender.Turbolift % "Shared"):
  import TurboliftLocal.{prog, MyError, MyReader, MyWriter, MyState}

  override def round1 =
    prog
    .handleWith(MyState.handlers.shared(0))
    .handleWith(MyWriter.handlers.shared)
    .handleWith(MyReader.handler(Sumh.LIMIT))
    .handleWith(MyError.handler)
    .runIO.get
    .map { case ((a, s), w) => (a, w, s) }

