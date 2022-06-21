package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import turbolift.!!
import turbolift.std_effects.{Reader, Writer, State, Except}


object Turbolift extends Sumh.Entry(Contender.Turbolift):
  case object MyExcept extends Except[String]
  case object MyReader extends Reader[Int]
  case object MyWriter extends Writer[Long]
  case object MyState extends State[Int]
  type MyExcept = MyExcept.type
  type MyReader = MyReader.type
  type MyWriter = MyWriter.type
  type MyState = MyState.type

  def prog: Int !! (MyExcept & MyReader & MyWriter & MyState) =
    for
      s <- MyState.get
      _ <- MyState.put(s + 1)
      _ <- MyWriter.tell(s)
      r <- MyReader.ask
      x <-
        if s < r
        then prog
        else !!.pure(s)
    yield x

  override def round1 =
    prog
    .runWith(
      MyState.handler(0) &&&!
      MyWriter.handler &&&!
      MyReader.handler(Sumh.LIMIT) &&&!
      MyExcept.handler
    )
    .map { case ((a, s), w) => (a, w, s) }
