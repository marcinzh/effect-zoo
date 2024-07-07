package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import kyo._


object Kyo extends Sumh.Entry(Contender.Kyo):
  def prog: Int < (Aborts[String] & Envs[Int] & Vars[Int] & Vars[Long] & IOs) =
    for
      s <- Vars.get[Int]
      _ <- Vars.set[Int](s + 1)
      _ <- Vars.update[Long](_ + s.toLong)
      r <- Envs.get[Int]
      x <- if s < r then IOs(prog) else s
    yield x


  override def round1 =
    KyoApp.run:
      Aborts.run[String]:
        Envs.run(Sumh.LIMIT):
          Vars.run(0L):
            Vars.run(0):
              for
                a <- prog
                b <- Vars.get[Long]
                c <- Vars.get[Int]
              yield (a, b, c)
