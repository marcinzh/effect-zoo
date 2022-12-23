package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import cats._
import cats.data._
import cats.implicits._
import org.atnos.eff._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._


object CatsEff extends Cdown.Entry(Contender.CatsEff):
  type MyState[A] = State[Int, A]

  def program[U](using MyState |= U): Eff[U, Int] =
    get.flatMap { n =>
      if n <= 0
      then n.pure
      else put(n - 1) *> program
    }

  override def round1 =
    program[Fx.fx1[MyState]]
    .runState(Cdown.LIMIT)
    .run
