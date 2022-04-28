package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import cats.data.State
import cats.implicits._


object CatsCore extends Cdown.Entry(Contender.CatsCore):
  def program: State[Int, Int] =
    State.get[Int].flatMap { n =>
      if n <= 0
      then n.pure
      else State.set(n - 1) *> program
    }

  override def round1 = program.run(Cdown.LIMIT).value
