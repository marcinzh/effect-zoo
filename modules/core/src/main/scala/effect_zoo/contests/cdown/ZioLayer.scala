package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import scala.util.chaining._
import zio._
import effect_zoo.auxx.zio_.BenchmarkRuntime
import effect_zoo.auxx.zio_.rws.layer.{State, StateLive}


object ZioLayer extends Cdown.Entry(Contender.ZIO % "Layer"):
  def program: URIO[State[Int], Int] =
    State.get[Int].flatMap: n =>
      if n <= 0
      then ZIO.succeed(n)
      else State.put(n - 1) *> program

  override def round1 =
    (program <*> State.get[Int])
    .provideLayer(StateLive.layer(Cdown.LIMIT))
    .pipe(BenchmarkRuntime.unsafeRun)
