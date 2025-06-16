package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import turbolift.!!
import turbolift.effects.{Reader, Writer, State, Error}


object Turbolift_Local extends Sumh.Entry(Contender.Turbolift % "Local"):
  case object MyError extends Error[String]
  case object MyReader extends Reader[Int]
  case object MyWriter extends Writer[Long]
  case object MyState extends State[Int]
  type MyError = MyError.type
  type MyReader = MyReader.type
  type MyWriter = MyWriter.type
  type MyState = MyState.type

  def prog: Int !! (MyError & MyReader & MyWriter & MyState) =
    MyState.getsEff: s =>
      for
        _ <- MyState.put(s + 1)
        _ <- MyWriter.tell(s)
        x <- MyReader.asksEff: r =>
          if s < r
          then prog
          else !!.pure(s)
      yield x

  override def round1 =
    prog
    .handleWith(MyState.handler(0))
    .handleWith(MyWriter.handler)
    .handleWith(MyReader.handler(Sumh.LIMIT))
    .handleWith(MyError.handler)
    .run
    .map { case ((a, s), w) => (a, w, s) }
