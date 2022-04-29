package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import zio.prelude.fx.ZPure


object Zpure extends Cdown.Entry(Contender.ZPure):
  def program: ZPure[Nothing, Int, Int, Any, Nothing, Int] =
    ZPure.get.flatMap { n =>
      if n <= 0
      then ZPure.succeed(n)
      else ZPure.set(n - 1) *> program
    }

  override def round1 = program.run(Cdown.LIMIT)
