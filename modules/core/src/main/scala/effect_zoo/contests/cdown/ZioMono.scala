package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import zio._
import effect_zoo.auxx.UnsafeRunZio._
import effect_zoo.auxx.zio_.rws.mono.State


object ZioMono extends Cdown.Entry(Contender.ZIO % "Mono"):
  object MyState extends State[Int]
  type MyState = MyState.Service

  def program: URIO[MyState, Int] =
    MyState.get.flatMap: n =>
      if n <= 0
      then ZIO.succeed(n)
      else MyState.put(n - 1) *> program

  override def round1 =
    (program <*> MyState.get)
    .provideLayer(MyState.Live.layer(Cdown.LIMIT))
    .unsafeRunZio
