package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import turbolift.!!
import turbolift.effects.{Reader, Writer, State, Error}
import turbolift.bindless._


object Turbolift_Local_Bindless extends Sumh.Entry(Contender.Turbolift % "Local_Bindless"):
  case object MyError extends Error[String]
  case object MyReader extends Reader[Int]
  case object MyWriter extends Writer[Long]
  case object MyState extends State[Int]
  type MyError = MyError.type
  type MyReader = MyReader.type
  type MyWriter = MyWriter.type
  type MyState = MyState.type

  def prog: Int !! (MyError & MyReader & MyWriter & MyState) =
    `do`:
      val s = MyState.get.!
      MyState.put(s + 1).!
      MyWriter.tell(s).!
      val r = MyReader.ask.!
      if s < r then prog.! else s

  override def round1 =
    prog
    .handleWith(MyState.handler(0))
    .handleWith(MyWriter.handler)
    .handleWith(MyReader.handler(Sumh.LIMIT))
    .handleWith(MyError.handler)
    .run
    .map { case ((a, s), w) => (a, w, s) }
