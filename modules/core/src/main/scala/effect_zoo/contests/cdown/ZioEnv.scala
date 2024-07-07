package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import scala.util.chaining._
import zio._
import effect_zoo.auxx.zio_.BenchmarkRuntime
import effect_zoo.auxx.zio_.rws.env.State


object ZioEnv extends Cdown.Entry(Contender.ZIO % "Env"):
  def program: URIO[State[Int], Int] =
    State.get[Int].flatMap: n =>
      if n <= 0
      then ZIO.succeed(n)
      else State.put(n - 1) *> program

  override def round1 =
    (for
      env <- State.env(Cdown.LIMIT)
      prog2 = program <*> State.get[Int]
      as <- prog2.provideEnvironment(env)
    yield as)
    .pipe(BenchmarkRuntime.unsafeRun)
