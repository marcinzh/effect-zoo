package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import kyo.*


object Kyo extends Cdown.Entry(Contender.Kyo):
  def program: Int < Vars[Int] =
    Vars.get[Int].flatMap { n =>
      if n <= 0
      then n
      else Vars.set(n - 1).andThen(program)
    }

  override def round1 =
    Vars.run(Cdown.LIMIT)(
      for {
        a <- program
        b <- Vars.get[Int]
      } yield (a, b)
    )
    .pure
