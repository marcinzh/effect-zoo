package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import kyo._


object Kyo_Direct extends Sumh.Entry(Contender.Kyo % "Direct"):
  def prog: Int < (Aborts[String] & Envs[Int] & Vars[Int] & Vars[Long]) =
    defer:
      val s = await(Vars.get[Int])
      await(Vars.set[Int](s + 1))
      await(Vars.update[Long](_ + s.toLong))
      val r = await(Envs.get[Int])
      if s < r then await(prog) else s


  override def round1 =
    Aborts.run[String]:
      Envs.run(Sumh.LIMIT):
        Vars.run(0L):
          Vars.run(0):
            for
              a <- prog
              b <- Vars.get[Long]
              c <- Vars.get[Int]
            yield (a, b, c)
    .pure
