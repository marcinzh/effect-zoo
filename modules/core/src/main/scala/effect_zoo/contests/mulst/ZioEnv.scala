package effect_zoo.contests.mulst
import effect_zoo.contests.{Contender, Mulst}
import zio._
import effect_zoo.auxx.UnsafeRunZio._
import ZioEnv_Aux._


object ZioEnv extends Mulst.Entry(Contender.ZIO % "Env"):
  class MyState1(override val ref: Ref[Int]) extends State[Int]
  class MyState2(override val ref: Ref[Int]) extends State[Int]
  class MyState3(override val ref: Ref[Int]) extends State[Int]
  class MyState4(override val ref: Ref[Int]) extends State[Int]
  class MyState5(override val ref: Ref[Int]) extends State[Int]
  object MyStateOps1 extends StateOps(MyState1.apply(_))
  object MyStateOps2 extends StateOps(MyState2.apply(_))
  object MyStateOps3 extends StateOps(MyState3.apply(_))
  object MyStateOps4 extends StateOps(MyState4.apply(_))
  object MyStateOps5 extends StateOps(MyState5.apply(_))


  override def round1 =
    def prog(n: Int): URIO[MyState1, Unit] =
      if n <= 0
      then ZIO.succeed(())
      else
        MyStateOps1.update(_ + 1) *>
        MyStateOps1.update(_ + 10) *>
        MyStateOps1.update(_ + 100) *>
        MyStateOps1.update(_ + 1000) *>
        MyStateOps1.update(_ + 10000) *>
        prog(n - 1)

    (for
      env1 <- MyStateOps1.env(0)
      prog2 = prog(Mulst.LIMIT) *> MyStateOps1.get
      x <- prog2.provideEnvironment(env1)
    yield x)
    .unsafeRunZio


  override def round2 =
    def prog(n: Int): URIO[MyState1 & MyState2, Unit] =
      if n <= 0
      then ZIO.succeed(())
      else
        MyStateOps1.update(_ + 1) *>
        MyStateOps2.update(_ + 10) *>
        MyStateOps1.update(_ + 100) *>
        MyStateOps2.update(_ + 1000) *>
        MyStateOps1.update(_ + 10000) *>
        prog(n - 1)

    (for
      env1 <- MyStateOps1.env(0)
      env2 <- MyStateOps2.env(0)
      prog2 = prog(Mulst.LIMIT) *> (MyStateOps1.get <*> MyStateOps2.get)
      x <- prog2.provideEnvironment(env1 ++ env2)
    yield x)
    .unsafeRunZio


  override def round3 =
    def prog(n: Int): URIO[MyState1 & MyState2 & MyState3, Unit] =
      if n <= 0
      then ZIO.succeed(())
      else
        MyStateOps1.update(_ + 1) *>
        MyStateOps2.update(_ + 10) *>
        MyStateOps3.update(_ + 100) *>
        MyStateOps1.update(_ + 1000) *>
        MyStateOps2.update(_ + 10000) *>
        prog(n - 1)

    (for
      env1 <- MyStateOps1.env(0)
      env2 <- MyStateOps2.env(0)
      env3 <- MyStateOps3.env(0)
      prog2 = prog(Mulst.LIMIT) *> (MyStateOps1.get <*> MyStateOps2.get <*> MyStateOps3.get)
      x <- prog2.provideEnvironment(env1 ++ env2 ++ env3)
    yield x)
    .unsafeRunZio


  override def round4 =
    def prog(n: Int): URIO[MyState1 & MyState2 & MyState3 & MyState4, Unit] =
      if n <= 0
      then ZIO.succeed(())
      else
        MyStateOps1.update(_ + 1) *>
        MyStateOps2.update(_ + 10) *>
        MyStateOps3.update(_ + 100) *>
        MyStateOps4.update(_ + 1000) *>
        MyStateOps1.update(_ + 10000) *>
        prog(n - 1)

    (for
      env1 <- MyStateOps1.env(0)
      env2 <- MyStateOps2.env(0)
      env3 <- MyStateOps3.env(0)
      env4 <- MyStateOps4.env(0)
      prog2 = prog(Mulst.LIMIT) *> (MyStateOps1.get <*> MyStateOps2.get <*> MyStateOps3.get <*> MyStateOps4.get)
      x <- prog2.provideEnvironment(env1 ++ env2 ++ env3 ++ env4)
    yield x)
    .unsafeRunZio


  override def round5 =
    def prog(n: Int): URIO[MyState1 & MyState2 & MyState3 & MyState4 & MyState5, Unit] =
      if n <= 0
      then ZIO.succeed(())
      else
        MyStateOps1.update(_ + 1) *>
        MyStateOps2.update(_ + 10) *>
        MyStateOps3.update(_ + 100) *>
        MyStateOps4.update(_ + 1000) *>
        MyStateOps5.update(_ + 10000) *>
        prog(n - 1)

    (for
      env1 <- MyStateOps1.env(0)
      env2 <- MyStateOps2.env(0)
      env3 <- MyStateOps3.env(0)
      env4 <- MyStateOps4.env(0)
      env5 <- MyStateOps5.env(0)
      prog2 = prog(Mulst.LIMIT) *> (MyStateOps1.get <*> MyStateOps2.get <*> MyStateOps3.get <*> MyStateOps4.get <*> MyStateOps5.get)
      x <- prog2.provideEnvironment(env1 ++ env2 ++ env3 ++ env4 ++ env5)
    yield x)
    .unsafeRunZio


object ZioEnv_Aux:
  trait State[S] { val ref: Ref[S] }

  class StateOps[S: Tag, T <: State[S]: Tag](ctor: Ref[S] => T):
    def get: URIO[T, S] = ZIO.environmentWithZIO[T](_.get.ref.get)
    def update(f: S => S): URIO[T, Unit] = ZIO.environmentWithZIO[T](_.get.ref.update(f))
    def env(initial: S): UIO[ZEnvironment[T]] = Ref.make(initial).flatMap(ref => ZIO.succeed(ZEnvironment(ctor(ref))))
