package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import turbolift.!!
import turbolift.effects.{Reader, Writer, State, Error}


object Turbolift_Local_Desugared extends Sumh.Entry(Contender.Turbolift % "Local_Desugared"):
  case object MyError extends Error[String]
  case object MyReader extends Reader[Int]
  case object MyWriter extends Writer[Long]
  case object MyState extends State[Int]
  type MyError = MyError.type
  type MyReader = MyReader.type
  type MyWriter = MyWriter.type
  type MyState = MyState.type

  def prog: Int !! (MyError & MyReader & MyWriter & MyState) =
    MyState.get.flatMap: s =>
      MyState.put(s + 1).flatMap: _ =>
        MyWriter.tell(s).flatMap: _ =>
          MyReader.ask.flatMap: r =>
            if s < r
            then prog
            else !!.pure(s)

  override def round1 =
    prog
    .handleWith(MyState.handler(0))
    .handleWith(MyWriter.handler)
    .handleWith(MyReader.handler(Sumh.LIMIT))
    .handleWith(MyError.handler)
    .run
    .map { case ((a, s), w) => (a, w, s) }
