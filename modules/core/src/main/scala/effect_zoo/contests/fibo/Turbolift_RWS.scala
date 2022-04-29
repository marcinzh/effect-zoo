package effect_zoo.contests.fibo
import effect_zoo.contests.{Fibo, Contender}
import turbolift.!!
import turbolift.std_effects.{Reader, Writer, State, Except}
import turbolift.extra_effects.ReaderWriterState.Syntax._


object Turbolift_RWS extends Fibo.Entry(Contender.Turbolift % "RWS"):
  case object MyExcept extends Except[String]
  case object MyReader extends Reader[Int]
  case object MyWriter extends Writer[Int]
  case object MyState extends State[Int]
  type MyExcept = MyExcept.type
  type MyReader = MyReader.type
  type MyWriter = MyWriter.type
  type MyState = MyState.type

  def fibo(a: Int): Int !! (MyExcept & MyReader & MyWriter & MyState) =
    for
      b <- MyState.get
      _ <- MyState.put(a)
      c = a + b
      _ <- MyWriter.tell(c)
      d <- MyReader.ask
      e <-
        if c < d
        then fibo(c)
        else !!.pure(c)
    yield e

  override def round1 =
    fibo(1)
    .handleWith((MyReader &! MyWriter &! MyState).handler(Fibo.LIMIT, 0))
    .handleWith(MyExcept.handler)
    .run
    .map { case (a, (w, s)) => (a, w, s) }
