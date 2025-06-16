package effect_zoo.contests.mulst
import effect_zoo.contests.{Contender, Mulst}
import effect_zoo.auxx.OpaqueInts._
import cats._
import cats.data._
import cats.implicits._
import org.atnos.eff._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._


object CatsEff extends Mulst.Entry(Contender.CatsEff):
  type MyState1[A] = State[Int1, A]
  type MyState2[A] = State[Int2, A]
  type MyState3[A] = State[Int3, A]
  type MyState4[A] = State[Int4, A]
  type MyState5[A] = State[Int5, A]

  override def round1 =
    def prog[U](n: Int)(using MyState1 |= U): Eff[U, Unit] =
      if n <= 0
      then Eff.unit
      else
        modify[U, Int1](_ + 1) *>
        modify[U, Int1](_ + 10) *>
        modify[U, Int1](_ + 100) *>
        modify[U, Int1](_ + 1000) *>
        modify[U, Int1](_ + 10000) *>
        prog(n - 1)

    prog(Mulst.LIMIT)
    .runState(0)
    .map { case (_, a) => a }
    .run


  override def round2 =
    def prog[U](n: Int)(using MyState1 |= U, MyState2 |= U): Eff[U, Unit] =
      if n <= 0
      then Eff.unit
      else
        modify[U, Int1](_ + 1) *>
        modify[U, Int2](_ + 10) *>
        modify[U, Int1](_ + 100) *>
        modify[U, Int2](_ + 1000) *>
        modify[U, Int1](_ + 10000) *>
        prog(n - 1)

    prog[Fx.fx2[MyState2, MyState1]](Mulst.LIMIT)
    .runStateU[Int, Fx.fx1[MyState2]](0)
    .runState[Int2](0.toInt2)
    .map { case ((_, a), b) => (a, b.unwrap) }
    .run


  override def round3 =
    def prog[U](n: Int)(using MyState1 |= U, MyState2 |= U, MyState3 |= U): Eff[U, Unit] =
      if n <= 0
      then Eff.unit
      else
        modify[U, Int1](_ + 1) *>
        modify[U, Int2](_ + 10) *>
        modify[U, Int3](_ + 100) *>
        modify[U, Int1](_ + 1000) *>
        modify[U, Int2](_ + 10000) *>
        prog(n - 1)

    prog[Fx.fx3[MyState1, MyState2, MyState3]](Mulst.LIMIT)
    .runState(0.toInt1)
    .runState(0.toInt2)
    .runState(0.toInt3)
    .map { case (((_, a), b), c) => (a, b.unwrap, c.unwrap) }
    .run


  override def round4 =
    def prog[U](n: Int)(using MyState1 |= U, MyState2 |= U, MyState3 |= U, MyState4 |= U): Eff[U, Unit] =
      if n <= 0
      then Eff.unit
      else
        modify[U, Int1](_ + 1) *>
        modify[U, Int2](_ + 10) *>
        modify[U, Int3](_ + 100) *>
        modify[U, Int4](_ + 1000) *>
        modify[U, Int1](_ + 10000) *>
        prog(n - 1)

    prog[Fx.fx4[MyState1, MyState2, MyState3, MyState4]](Mulst.LIMIT)
    .runState(0.toInt1)
    .runState(0.toInt2)
    .runState(0.toInt3)
    .runState(0.toInt4)
    .map { case ((((_, a), b), c), d) => (a, b.unwrap, c.unwrap, d.unwrap) }
    .run


  override def round5 =
    def prog[U](n: Int)(using MyState1 |= U, MyState2 |= U, MyState3 |= U, MyState4 |= U, MyState5 |= U): Eff[U, Unit] =
      if n <= 0
      then Eff.unit
      else
        modify[U, Int1](_ + 1) *>
        modify[U, Int2](_ + 10) *>
        modify[U, Int3](_ + 100) *>
        modify[U, Int4](_ + 1000) *>
        modify[U, Int5](_ + 10000) *>
        prog(n - 1)

    prog[Fx.fx5[MyState1, MyState2, MyState3, MyState4, MyState5]](Mulst.LIMIT)
    .runState(0.toInt1)
    .runState(0.toInt2)
    .runState(0.toInt3)
    .runState(0.toInt4)
    .runState(0.toInt5)
    .map { case (((((_, a), b), c), d), e) => (a, b.unwrap, c.unwrap, d.unwrap, e.unwrap) }
    .run
