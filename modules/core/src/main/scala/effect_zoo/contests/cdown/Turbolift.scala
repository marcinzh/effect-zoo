package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import turbolift.!!
import turbolift.std_effects.State


object Turbolift extends Cdown.Entry(Contender.Turbolift):
  case object MyState extends State[Int]
  type MyState = MyState.type

  def program: Int !! MyState =
    MyState.get.flatMap { n =>
      if n <= 0
      then !!.pure(n)
      else MyState.put(n - 1) &&! program
    }

  override def round1 = program.handleWith(MyState.handler(Cdown.LIMIT)).run
