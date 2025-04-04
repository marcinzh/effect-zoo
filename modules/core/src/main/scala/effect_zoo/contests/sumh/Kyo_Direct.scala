package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import kyo.*

object Kyo_Direct extends Sumh.Entry(Contender.Kyo % "Direct"):
  def prog: Int < (Abort[String] & Env[Int] & Var[Int] & Var[Long]) =
    defer:
      val s = (Var.get[Int]).now
      (Var.set[Int](s + 1)).now
      (Var.update[Long](_ + s.toLong)).now
      val r = (Env.get[Int]).now
      if s < r then (prog).now else s

  override def round1 =
    Abort
      .run[String]:
        Env.run(Sumh.LIMIT):
          Var.run(0L):
            Var.run(0):
              defer:
                val a = (prog).now
                val b = (Var.get[Long]).now
                val c = (Var.get[Int]).now
                (a, b, c)
      .eval
      .toEither
      .left
      .map(e => e.toString())
