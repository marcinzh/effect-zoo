package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import scala.util.chaining._
import zio._
import effect_zoo.auxx.zio_.BenchmarkRuntime


object ZioRef extends Cdown.Entry(Contender.ZIO % "Ref"):
  def program: URIO[Ref[Int], Int] =
    ZIO.accessM { ref =>
      ref.get.flatMap { n =>
        if n <= 0
        then ZIO.succeed(n)
        else ref.set(n - 1) *> program
      }
    }

  override def round1 =
    (for
      ref <- Ref.make(Cdown.LIMIT)
      prog = program <*> ref.get
      as <- prog.provide(ref)
    yield as)
    .pipe(BenchmarkRuntime.unsafeRun)
