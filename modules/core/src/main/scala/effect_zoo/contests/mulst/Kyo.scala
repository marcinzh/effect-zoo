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

    prog(Mulst.LIMIT)
      .pipe(Var.runTuple(0))
      .map(_._1)
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

    prog(Mulst.LIMIT)
      .pipe(Var.runTuple(0.toInt1))
      .pipe(Var.runTuple(0.toInt2))
      .map {
        case (int2, (int1, _)) => (int1.toInt, int2.toInt)
      }
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

    prog(Mulst.LIMIT)
      .pipe(Var.runTuple(0.toInt1))
      .pipe(Var.runTuple(0.toInt2))
      .pipe(Var.runTuple(0.toInt3))
      .map {
        case (int3, (int2, (int1, _))) => (int1.toInt, int2.toInt, int3.toInt)
      }
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

    prog(Mulst.LIMIT)
      .pipe(Var.runTuple(0.toInt1))
      .pipe(Var.runTuple(0.toInt2))
      .pipe(Var.runTuple(0.toInt3))
      .pipe(Var.runTuple(0.toInt4))
      .map {
        case (int4, (int3, (int2, (int1, _)))) => (int1.toInt, int2.toInt, int3.toInt, int4.toInt)
      }
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

    prog(Mulst.LIMIT)
      .pipe(Var.runTuple(0.toInt1))
      .pipe(Var.runTuple(0.toInt2))
      .pipe(Var.runTuple(0.toInt3))
      .pipe(Var.runTuple(0.toInt4))
      .pipe(Var.runTuple(0.toInt5))
      .map {
        case (int5, (int4, (int3, (int2, (int1, _))))) => (int1.toInt, int2.toInt, int3.toInt, int4.toInt, int5.toInt)
      }
      .eval

