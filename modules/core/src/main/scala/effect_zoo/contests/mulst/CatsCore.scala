package effect_zoo.contests.mulst
import effect_zoo.contests.{Contender, Mulst}
import scala.util.chaining._
import cats.{Eval, Now}
import cats.data.StateT
import cats.implicits._


object CatsCore extends Mulst.Entry(Contender.CatsCore):
  type Eff0[A] = Eval[A]
  type Eff1[A] = StateT[Eff0, Int, A]
  type Eff2[A] = StateT[Eff1, Int, A]
  type Eff3[A] = StateT[Eff2, Int, A]
  type Eff4[A] = StateT[Eff3, Int, A]
  type Eff5[A] = StateT[Eff4, Int, A]


  override def round1 =
    def lift0[A](x: Eff0[A]): Eff1[A] = lift1(StateT.liftF(x))
    def lift1[A](x: Eff1[A]): Eff1[A] = x

    def prog(n: Int): Eff1[Unit] =
      if n <= 0
      then lift0(Now(()))
      else
        for
          _ <- lift1(StateT.modify(_ + 1))
          _ <- lift1(StateT.modify(_ + 10))
          _ <- lift1(StateT.modify(_ + 100))
          _ <- lift1(StateT.modify(_ + 1000))
          _ <- lift1(StateT.modify(_ + 10000))
          _ <- prog(n - 1)
        yield ()

    prog(Mulst.LIMIT)
    .run(0).value
    ._1


  override def round2 =
    def lift0[A](x: Eff0[A]): Eff2[A] = lift1(StateT.liftF(x))
    def lift1[A](x: Eff1[A]): Eff2[A] = lift2(StateT.liftF(x))
    def lift2[A](x: Eff2[A]): Eff2[A] = x

    def prog(n: Int): Eff2[Unit] =
      if n <= 0
      then lift0(Now(()))
      else
        for
          _ <- lift1(StateT.modify(_ + 1))
          _ <- lift2(StateT.modify(_ + 10))
          _ <- lift1(StateT.modify(_ + 100))
          _ <- lift2(StateT.modify(_ + 1000))
          _ <- lift1(StateT.modify(_ + 10000))
          _ <- prog(n - 1)
        yield ()

    prog(Mulst.LIMIT)
    .run(0).run(0).value
    .pipe { case (a, (b, _)) => (a, b) }


  override def round3 =
    def lift0[A](x: Eff0[A]): Eff3[A] = lift1(StateT.liftF(x))
    def lift1[A](x: Eff1[A]): Eff3[A] = lift2(StateT.liftF(x))
    def lift2[A](x: Eff2[A]): Eff3[A] = lift3(StateT.liftF(x))
    def lift3[A](x: Eff3[A]): Eff3[A] = x

    def prog(n: Int): Eff3[Unit] =
      if n <= 0
      then lift0(Now(()))
      else
        for
          _ <- lift1(StateT.modify(_ + 1))
          _ <- lift2(StateT.modify(_ + 10))
          _ <- lift3(StateT.modify(_ + 100))
          _ <- lift1(StateT.modify(_ + 1000))
          _ <- lift2(StateT.modify(_ + 10000))
          _ <- prog(n - 1)
        yield ()

    prog(Mulst.LIMIT)
    .run(0).run(0).run(0).value
    .pipe { case (a, (b, (c, _))) => (a, b, c) }


  override def round4 =
    def lift0[A](x: Eff0[A]): Eff4[A] = lift1(StateT.liftF(x))
    def lift1[A](x: Eff1[A]): Eff4[A] = lift2(StateT.liftF(x))
    def lift2[A](x: Eff2[A]): Eff4[A] = lift3(StateT.liftF(x))
    def lift3[A](x: Eff3[A]): Eff4[A] = lift4(StateT.liftF(x))
    def lift4[A](x: Eff4[A]): Eff4[A] = x

    def prog(n: Int): Eff4[Unit] =
      if n <= 0
      then lift0(Now(()))
      else
        for
          _ <- lift1(StateT.modify(_ + 1))
          _ <- lift2(StateT.modify(_ + 10))
          _ <- lift3(StateT.modify(_ + 100))
          _ <- lift4(StateT.modify(_ + 1000))
          _ <- lift1(StateT.modify(_ + 10000))
          _ <- prog(n - 1)
        yield ()

    prog(Mulst.LIMIT)
    .run(0).run(0).run(0).run(0).value
    .pipe { case (a, (b, (c, (d, _)))) => (a, b, c, d) }


  override def round5 =
    def lift0[A](x: Eff0[A]): Eff5[A] = lift1(StateT.liftF(x))
    def lift1[A](x: Eff1[A]): Eff5[A] = lift2(StateT.liftF(x))
    def lift2[A](x: Eff2[A]): Eff5[A] = lift3(StateT.liftF(x))
    def lift3[A](x: Eff3[A]): Eff5[A] = lift4(StateT.liftF(x))
    def lift4[A](x: Eff4[A]): Eff5[A] = lift5(StateT.liftF(x))
    def lift5[A](x: Eff5[A]): Eff5[A] = x

    def prog(n: Int): Eff5[Unit] =
      if n <= 0
      then lift0(Now(()))
      else
        for
          _ <- lift1(StateT.modify(_ + 1))
          _ <- lift2(StateT.modify(_ + 10))
          _ <- lift3(StateT.modify(_ + 100))
          _ <- lift4(StateT.modify(_ + 1000))
          _ <- lift5(StateT.modify(_ + 10000))
          _ <- prog(n - 1)
        yield ()

    prog(Mulst.LIMIT)
    .run(0).run(0).run(0).run(0).run(0).value
    .pipe { case (a, (b, (c, (d, (e, _))))) => (a, b, c, d, e) }
