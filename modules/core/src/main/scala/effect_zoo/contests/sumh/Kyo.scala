package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import kyo.*

import kyo.kernel.`<`.*

object Kyo extends Sumh.Entry(Contender.Kyo):
  def prog: Int < (Abort[String] & Env[Int] & Var[Int] & Var[Long]) =
    for
      s <- Var.get[Int]
      _ <- Var.set[Int](s + 1)
      _ <- Var.update[Long](_ + s.toLong)
      r <- Env.get[Int]
      x <- if s < r then (prog) else s
    yield x

  override def round1 =

    val r = Abort.run[String]:
      Env.run(Sumh.LIMIT):
        Var.run(0L):
          Var.run(0):
            for
              a <- prog
              b <- Var.get[Long]
              c <- Var.get[Int]
            yield (a, b, c)

    r.eval.toEither.left
      .map(e => e.toString())
