package effect_zoo.contests.mulst
import effect_zoo.contests.{Contender, Mulst}
import scala.util.chaining._
import zio._
import effect_zoo.auxx.zio_.BenchmarkRuntime
import effect_zoo.auxx.zio_.rws.mono.State


object ZioLayer extends Mulst.Entry(Contender.ZIO % "Mono"):
  case object MyState1 extends State[Int]
  case object MyState2 extends State[Int]
  case object MyState3 extends State[Int]
  case object MyState4 extends State[Int]
  case object MyState5 extends State[Int]
  type MyState1 = Has[MyState1.Service]
  type MyState2 = Has[MyState2.Service]
  type MyState3 = Has[MyState3.Service]
  type MyState4 = Has[MyState4.Service]
  type MyState5 = Has[MyState5.Service]


  override def round1 =
    def prog(n: Int): URIO[MyState1, Unit] =
      if n <= 0
      then ZIO.unit
      else
        MyState1.update(_ + 1) *>
        MyState1.update(_ + 10) *>
        MyState1.update(_ + 100) *>
        MyState1.update(_ + 1000) *>
        MyState1.update(_ + 10000) *>
        prog(n - 1)

    (prog(Mulst.LIMIT) *> MyState1.get)
    .provideLayer(MyState1.Live.layer(0))
    .pipe(BenchmarkRuntime.unsafeRun)


  override def round2 =
    def prog(n: Int): URIO[MyState1 & MyState2, Unit] =
      if n <= 0
      then ZIO.unit
      else
        MyState1.update(_ + 1) *>
        MyState2.update(_ + 10) *>
        MyState1.update(_ + 100) *>
        MyState2.update(_ + 1000) *>
        MyState1.update(_ + 10000) *>
        prog(n - 1)

    (prog(Mulst.LIMIT) *> (MyState1.get <*> MyState2.get))
    .provideLayer(MyState1.Live.layer(0) ++ MyState2.Live.layer(0))
    .pipe(BenchmarkRuntime.unsafeRun)


  override def round3 =
    def prog(n: Int): URIO[MyState1 & MyState2 & MyState3, Unit] =
      if n <= 0
      then ZIO.unit
      else
        MyState1.update(_ + 1) *>
        MyState2.update(_ + 10) *>
        MyState3.update(_ + 100) *>
        MyState1.update(_ + 1000) *>
        MyState2.update(_ + 10000) *>
        prog(n - 1)

    (prog(Mulst.LIMIT) <*> MyState1.get <*> MyState2.get <*> MyState3.get)
    .map { case (((_, a), b), c) => (a, b, c) }
    .provideLayer(MyState1.Live.layer(0) ++ MyState2.Live.layer(0) ++ MyState3.Live.layer(0))
    .pipe(BenchmarkRuntime.unsafeRun)


  override def round4 =
    def prog(n: Int): URIO[MyState1 & MyState2 & MyState3 & MyState4, Unit] =
      if n <= 0
      then ZIO.unit
      else
        MyState1.update(_ + 1) *>
        MyState2.update(_ + 10) *>
        MyState3.update(_ + 100) *>
        MyState4.update(_ + 1000) *>
        MyState1.update(_ + 10000) *>
        prog(n - 1)

    (prog(Mulst.LIMIT) <*> MyState1.get <*> MyState2.get <*> MyState3.get <*> MyState4.get)
    .map { case ((((_, a), b), c), d) => (a, b, c, d) }
    .provideLayer(
      MyState1.Live.layer(0) ++
      MyState2.Live.layer(0) ++
      MyState3.Live.layer(0) ++
      MyState4.Live.layer(0)
    )
    .pipe(BenchmarkRuntime.unsafeRun)


  override def round5 =
    def prog(n: Int): URIO[MyState1 & MyState2 & MyState3 & MyState4 & MyState5, Unit] =
      if n <= 0
      then ZIO.unit
      else
        MyState1.update(_ + 1) *>
        MyState2.update(_ + 10) *>
        MyState3.update(_ + 100) *>
        MyState4.update(_ + 1000) *>
        MyState5.update(_ + 10000) *>
        prog(n - 1)

    (prog(Mulst.LIMIT) <*> MyState1.get <*> MyState2.get <*> MyState3.get <*> MyState4.get <*> MyState5.get)
    .map { case (((((_, a), b), c), d), e) => (a, b, c, d, e) }
    .provideLayer(
      MyState1.Live.layer(0) ++
      MyState2.Live.layer(0) ++
      MyState3.Live.layer(0) ++
      MyState4.Live.layer(0) ++
      MyState5.Live.layer(0)
    )
    .pipe(BenchmarkRuntime.unsafeRun)
