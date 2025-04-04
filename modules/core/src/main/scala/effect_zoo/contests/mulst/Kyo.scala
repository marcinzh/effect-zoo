package effect_zoo.contests.mulst
import effect_zoo.contests.{Contender, Mulst}
import effect_zoo.auxx.OpaqueInts._
import kyo.*

object Kyo extends Mulst.Entry(Contender.Kyo):
  override def round1 =
    def prog(n: Int): Unit < Var[Int] =
      if n <= 0
      then ()
      else
        /**/ Var
          .update[Int](_ + 1)
          .andThen(Var.update[Int](_ + 10))
          .andThen(Var.update[Int](_ + 100))
          .andThen(Var.update[Int](_ + 1000))
          .andThen(Var.update[Int](_ + 10000))
          .andThen(prog(n - 1))

    Var
      .run(0):
        for
          _ <- prog(Mulst.LIMIT)
          a <- Var.get[Int]
        yield a
      .eval

  override def round2 =
    def prog(n: Int): Unit < (Var[Int1] & Var[Int2]) =
      if n <= 0
      then ()
      else
        /**/ Var
          .update[Int1](_ + 1)
          .andThen(Var.update[Int2](_ + 10))
          .andThen(Var.update[Int1](_ + 100))
          .andThen(Var.update[Int2](_ + 1000))
          .andThen(Var.update[Int1](_ + 10000))
          .andThen(prog(n - 1))

    Var
      .run(0.toInt2):
        Var.run(0.toInt1):
          for
            _ <- prog(Mulst.LIMIT)
            a <- Var.get[Int1]
            b <- Var.get[Int2].map(_.toInt)
          yield (a, b)
      .eval

  override def round3 =
    def prog(n: Int): Unit < (Var[Int1] & Var[Int2] & Var[Int3]) =
      if n <= 0
      then ()
      else
        /**/ Var
          .update[Int1](_ + 1)
          .andThen(Var.update[Int2](_ + 10))
          .andThen(Var.update[Int3](_ + 100))
          .andThen(Var.update[Int1](_ + 1000))
          .andThen(Var.update[Int2](_ + 10000))
          .andThen(prog(n - 1))

    Var
      .run(0.toInt3):
        Var.run(0.toInt2):
          Var.run(0.toInt1):
            for
              _ <- prog(Mulst.LIMIT)
              a <- Var.get[Int1]
              b <- Var.get[Int2].map(_.toInt)
              c <- Var.get[Int3].map(_.toInt)
            yield (a, b, c)
      .eval

  override def round4 =
    def prog(n: Int): Unit < (Var[Int1] & Var[Int2] & Var[Int3] & Var[Int4]) =
      if n <= 0
      then ()
      else
        /**/ Var
          .update[Int1](_ + 1)
          .andThen(Var.update[Int2](_ + 10))
          .andThen(Var.update[Int3](_ + 100))
          .andThen(Var.update[Int4](_ + 1000))
          .andThen(Var.update[Int1](_ + 10000))
          .andThen(prog(n - 1))

    Var
      .run(0.toInt4):
        Var.run(0.toInt3):
          Var.run(0.toInt2):
            Var.run(0.toInt1):
              for
                _ <- prog(Mulst.LIMIT)
                a <- Var.get[Int1]
                b <- Var.get[Int2].map(_.toInt)
                c <- Var.get[Int3].map(_.toInt)
                d <- Var.get[Int4].map(_.toInt)
              yield (a, b, c, d)
      .eval

  override def round5 =
    def prog(
        n: Int
    ): Unit < (Var[Int1] & Var[Int2] & Var[Int3] & Var[Int4] & Var[Int5]) =
      if n <= 0
      then ()
      else
        /**/ Var
          .update[Int1](_ + 1)
          .andThen(Var.update[Int2](_ + 10))
          .andThen(Var.update[Int3](_ + 100))
          .andThen(Var.update[Int4](_ + 1000))
          .andThen(Var.update[Int5](_ + 10000))
          .andThen(prog(n - 1))

    Var
      .run(0.toInt5):
        Var.run(0.toInt4):
          Var.run(0.toInt3):
            Var.run(0.toInt2):
              Var.run(0.toInt1):
                for
                  _ <- prog(Mulst.LIMIT)
                  a <- Var.get[Int1]
                  b <- Var.get[Int2].map(_.toInt)
                  c <- Var.get[Int3].map(_.toInt)
                  d <- Var.get[Int4].map(_.toInt)
                  e <- Var.get[Int5].map(_.toInt)
                yield (a, b, c, d, e)
      .eval
