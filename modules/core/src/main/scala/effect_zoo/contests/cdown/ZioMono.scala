package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import scala.util.chaining._
import zio._
import effect_zoo.aux.zio_.BenchmarkRuntime
import effect_zoo.aux.zio_.rws.mono.State


object ZioMono extends Cdown.Entry(Contender.ZIO % "Mono"):
  object MyState extends State[Int]
  type MyState = Has[MyState.Service]

  def program: URIO[MyState, Int] =
    MyState.get.flatMap { n =>
      if n <= 0
      then ZIO.succeed(n)
      else MyState.put(n - 1) *> program
    }

  override def round1 =
    (program <*> MyState.get)
    .provideLayer(MyState.Live.layer(Cdown.LIMIT))
    .pipe(BenchmarkRuntime.unsafeRun)
