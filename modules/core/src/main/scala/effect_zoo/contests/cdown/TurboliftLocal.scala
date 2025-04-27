package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import turbolift.!!
import turbolift.effects.State


object TurboliftLocal extends Cdown.Entry(Contender.Turbolift % "Local"):
  case object MyState extends State[Int]
  type MyState = MyState.type

  def program: Int !! MyState =
    MyState.getsEff: n =>
      if n <= 0
      then !!.pure(n)
      else MyState.put(n - 1) &&! program

  override def round1 =
    program
    .handleWith(MyState.handler(Cdown.LIMIT))
    .run
