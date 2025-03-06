package effect_zoo.contests.mulst
import effect_zoo.contests.{Contender, Mulst}
import effect_zoo.auxx.OpaqueInts._
import kyo.*


object Kyo extends Mulst.Entry(Contender.Kyo):
  override def round1 = 
    def prog(n: Int): Unit < Vars[Int] =
      if n <= 0
      then ()
      else
        /**/     Vars.update[Int](_ + 1)
        .andThen(Vars.update[Int](_ + 10))
        .andThen(Vars.update[Int](_ + 100))
        .andThen(Vars.update[Int](_ + 1000))
        .andThen(Vars.update[Int](_ + 10000))
        .andThen(prog(n - 1))

    Vars.run(0):
      for
        _ <- prog(Mulst.LIMIT)
        a <- Vars.get[Int]
      yield a
    .pure


  override def round2 =
    def prog(n: Int): Unit < (Vars[Int1] & Vars[Int2]) =
      if n <= 0
      then ()
      else
        /**/     Vars.update[Int1](_ + 1)
        .andThen(Vars.update[Int2](_ + 10))
        .andThen(Vars.update[Int1](_ + 100))
        .andThen(Vars.update[Int2](_ + 1000))
        .andThen(Vars.update[Int1](_ + 10000))
        .andThen(prog(n - 1))

    Vars.run(0.toInt2):
      Vars.run(0.toInt1):
        for
          _ <- prog(Mulst.LIMIT)
          a <- Vars.get[Int1]
          b <- Vars.get[Int2].map(_.toInt)
        yield (a, b)
    .pure


  override def round3 =
    def prog(n: Int): Unit < (Vars[Int1] & Vars[Int2] & Vars[Int3]) =
      if n <= 0
      then ()
      else
        /**/     Vars.update[Int1](_ + 1)
        .andThen(Vars.update[Int2](_ + 10))
        .andThen(Vars.update[Int3](_ + 100))
        .andThen(Vars.update[Int1](_ + 1000))
        .andThen(Vars.update[Int2](_ + 10000))
        .andThen(prog(n - 1))

    Vars.run(0.toInt3):
      Vars.run(0.toInt2):
        Vars.run(0.toInt1):
          for
            _ <- prog(Mulst.LIMIT)
            a <- Vars.get[Int1]
            b <- Vars.get[Int2].map(_.toInt)
            c <- Vars.get[Int3].map(_.toInt)
          yield (a, b, c)
    .pure


  override def round4 =
    def prog(n: Int): Unit < (Vars[Int1] & Vars[Int2] & Vars[Int3] & Vars[Int4]) =
      if n <= 0
      then ()
      else
        /**/     Vars.update[Int1](_ + 1)
        .andThen(Vars.update[Int2](_ + 10))
        .andThen(Vars.update[Int3](_ + 100))
        .andThen(Vars.update[Int4](_ + 1000))
        .andThen(Vars.update[Int1](_ + 10000))
        .andThen(prog(n - 1))

    Vars.run(0.toInt4):
      Vars.run(0.toInt3):
        Vars.run(0.toInt2):
          Vars.run(0.toInt1):
            for
              _ <- prog(Mulst.LIMIT)
              a <- Vars.get[Int1]
              b <- Vars.get[Int2].map(_.toInt)
              c <- Vars.get[Int3].map(_.toInt)
              d <- Vars.get[Int4].map(_.toInt)
            yield (a, b, c, d)
    .pure


  override def round5 =
    def prog(n: Int): Unit < (Vars[Int1] & Vars[Int2] & Vars[Int3] & Vars[Int4] & Vars[Int5]) =
      if n <= 0
      then ()
      else
        /**/     Vars.update[Int1](_ + 1)
        .andThen(Vars.update[Int2](_ + 10))
        .andThen(Vars.update[Int3](_ + 100))
        .andThen(Vars.update[Int4](_ + 1000))
        .andThen(Vars.update[Int5](_ + 10000))
        .andThen(prog(n - 1))

    Vars.run(0.toInt5):
      Vars.run(0.toInt4):
        Vars.run(0.toInt3):
          Vars.run(0.toInt2):
            Vars.run(0.toInt1):
              for
                _ <- prog(Mulst.LIMIT)
                a <- Vars.get[Int1]
                b <- Vars.get[Int2].map(_.toInt)
                c <- Vars.get[Int3].map(_.toInt)
                d <- Vars.get[Int4].map(_.toInt)
                e <- Vars.get[Int5].map(_.toInt)
              yield (a, b, c, d, e)
    .pure
