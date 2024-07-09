package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import kyo._


object Kyo_Desugared extends Sumh.Entry(Contender.Kyo % "Desugared"):
  def prog: Int < (Aborts[String] & Envs[Int] & Vars[Int] & Vars[Long]) =
    Vars.get[Int].flatMap: s =>
      Vars.set[Int](s + 1).flatMap: _ =>
        Vars.update[Long](_ + s.toLong).flatMap: _ =>
          Envs.get[Int].flatMap: r =>
            if s < r then prog else s


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
