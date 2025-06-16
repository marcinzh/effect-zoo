package effect_zoo.contests.mulst
import effect_zoo.contests.{Contender, Mulst}
import effect_zoo.auxx.UnsafeRunZio._
import scala.util.chaining._
import zio._
import ZioStateful_Aux._


object ZioStateful extends Mulst.Entry(Contender.ZIO % "Stateful"):
  class MyState1(override val value: Int) extends State[Int]
  class MyState2(override val value: Int) extends State[Int]
  class MyState3(override val value: Int) extends State[Int]
  class MyState4(override val value: Int) extends State[Int]
  class MyState5(override val value: Int) extends State[Int]
  object MyStateOps1 extends StateOps(MyState1.apply(_))
  object MyStateOps2 extends StateOps(MyState2.apply(_))
  object MyStateOps3 extends StateOps(MyState3.apply(_))
  object MyStateOps4 extends StateOps(MyState4.apply(_))
  object MyStateOps5 extends StateOps(MyState5.apply(_))
  

  override def round1 =
    def prog(n: Int): URIO[ZState[MyState1], Unit] =
      if n <= 0
      then ZIO.succeed(())
      else
        MyStateOps1.update(_ + 1) *>
        MyStateOps1.update(_ + 10) *>
        MyStateOps1.update(_ + 100) *>
        MyStateOps1.update(_ + 1000) *>
        MyStateOps1.update(_ + 10000) *>
        prog(n - 1)

    (prog(Mulst.LIMIT) *> MyStateOps1.get)
    .pipe(ZIO.stateful[Any](MyState1(0)))
    .unsafeRunZio


  override def round2 =
    def prog(n: Int): URIO[ZState[MyState1] & ZState[MyState2], Unit] =
      if n <= 0
      then ZIO.succeed(())
      else
        MyStateOps1.update(_ + 1) *>
        MyStateOps2.update(_ + 10) *>
        MyStateOps1.update(_ + 100) *>
        MyStateOps2.update(_ + 1000) *>
        MyStateOps1.update(_ + 10000) *>
        prog(n - 1)

    (prog(Mulst.LIMIT) *> (MyStateOps1.get <*> MyStateOps2.get))
    .pipe(ZIO.stateful[ZState[MyState2]](MyState1(0)))
    .pipe(ZIO.stateful[Any](MyState2(0)))
    .unsafeRunZio


  override def round3 =
    def prog(n: Int): URIO[ZState[MyState1] & ZState[MyState2] & ZState[MyState3], Unit] =
      if n <= 0
      then ZIO.succeed(())
      else
        MyStateOps1.update(_ + 1) *>
        MyStateOps2.update(_ + 10) *>
        MyStateOps3.update(_ + 100) *>
        MyStateOps1.update(_ + 1000) *>
        MyStateOps2.update(_ + 10000) *>
        prog(n - 1)

    (prog(Mulst.LIMIT) *> (MyStateOps1.get <*> MyStateOps2.get <*> MyStateOps3.get))
    .pipe(ZIO.stateful[ZState[MyState3] & ZState[MyState2]](MyState1(0)))
    .pipe(ZIO.stateful[ZState[MyState3]](MyState2(0)))
    .pipe(ZIO.stateful[Any](MyState3(0)))
    .unsafeRunZio
    

  override def round4 =
    def prog(n: Int): URIO[ZState[MyState1] & ZState[MyState2] & ZState[MyState3] & ZState[MyState4], Unit] =
      if n <= 0
      then ZIO.succeed(())
      else
        MyStateOps1.update(_ + 1) *>
        MyStateOps2.update(_ + 10) *>
        MyStateOps3.update(_ + 100) *>
        MyStateOps4.update(_ + 1000) *>
        MyStateOps1.update(_ + 10000) *>
        prog(n - 1)

    (prog(Mulst.LIMIT) *> (MyStateOps1.get <*> MyStateOps2.get <*> MyStateOps3.get <*> MyStateOps4.get))
    .pipe(ZIO.stateful[ZState[MyState4] & ZState[MyState3] & ZState[MyState2]](MyState1(0)))
    .pipe(ZIO.stateful[ZState[MyState4] & ZState[MyState3]](MyState2(0)))
    .pipe(ZIO.stateful[ZState[MyState4]](MyState3(0)))
    .pipe(ZIO.stateful[Any](MyState4(0)))
    .unsafeRunZio


  override def round5 =
    def prog(n: Int): URIO[ZState[MyState1] & ZState[MyState2] & ZState[MyState3] & ZState[MyState4] & ZState[MyState5], Unit] =
      if n <= 0
      then ZIO.succeed(())
      else
        MyStateOps1.update(_ + 1) *>
        MyStateOps2.update(_ + 10) *>
        MyStateOps3.update(_ + 100) *>
        MyStateOps4.update(_ + 1000) *>
        MyStateOps5.update(_ + 10000) *>
        prog(n - 1)

    (prog(Mulst.LIMIT) *> (MyStateOps1.get <*> MyStateOps2.get <*> MyStateOps3.get <*> MyStateOps4.get <*> MyStateOps5.get))
    .pipe(ZIO.stateful[ZState[MyState5] & ZState[MyState4] & ZState[MyState3] & ZState[MyState2]](MyState1(0)))
    .pipe(ZIO.stateful[ZState[MyState5] & ZState[MyState4] & ZState[MyState3]](MyState2(0)))
    .pipe(ZIO.stateful[ZState[MyState5] & ZState[MyState4]](MyState3(0)))
    .pipe(ZIO.stateful[ZState[MyState5]](MyState4(0)))
    .pipe(ZIO.stateful[Any](MyState5(0)))
    .unsafeRunZio



object ZioStateful_Aux:
  trait State[S] { val value: S }

  class StateOps[S: Tag, T <: State[S]: Tag](ctor: S => T):
    def get = ZIO.getStateWith[T](_.value)
    def update(f: S => S) = ZIO.updateState[T](x => ctor(f(x.value)))
