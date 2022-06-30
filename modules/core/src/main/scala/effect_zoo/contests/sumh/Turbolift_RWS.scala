package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import turbolift.!!
import turbolift.std_effects.{Reader, Writer, State, Error}
import turbolift.extra_effects.ReaderWriterState.Syntax._


object Turbolift_RWS extends Sumh.Entry(Contender.Turbolift % "RWS"):
  case object MyError extends Error[String]
  case object MyReader extends Reader[Int]
  case object MyWriter extends Writer[Long]
  case object MyState extends State[Int]
  type MyError = MyError.type
  type MyReader = MyReader.type
  type MyWriter = MyWriter.type
  type MyState = MyState.type

  def prog: Int !! (MyError & MyReader & MyWriter & MyState) =
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
    .handleWith((MyReader &! MyWriter &! MyState).handler(Sumh.LIMIT, 0))
    .handleWith(MyError.handler)
    .run
    .map { case (a, (w, s)) => (a, w, s) }
