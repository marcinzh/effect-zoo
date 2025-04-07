package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import kyo.*

object Kyo extends Cdown.Entry(Contender.Kyo):
  def program: Int < Var[Int] =
    Var
      .get[Int]
      .flatMap: n =>
        if n <= 0
        then n
        else Var.set(n - 1).andThen(program)

  override def round1 =
    Var
      .run(Cdown.LIMIT):
        for
          a <- program
          b <- Var.get[Int]
        yield (a, b)
      .eval
