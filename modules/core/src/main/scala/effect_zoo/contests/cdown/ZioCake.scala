package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import scala.util.chaining._
import zio._
import effect_zoo.auxx.zio_.BenchmarkRuntime
import effect_zoo.auxx.zio_.rws.cake.{Reader, Writer, State, Cake}


object ZioCake extends Cdown.Entry(Contender.ZIO % "Cake"):
  def program: URIO[State[Int], Int] =
    State.get[Int].flatMap { n =>
      if n <= 0
      then ZIO.succeed(n)
      else State.put(n - 1) *> program
    }

  override def round1 =
    (program <*> State.get[Int])
    .provide(ZLayer.fromZIO(Cake[Int, Int, Int](0, Cdown.LIMIT)))
    .pipe(BenchmarkRuntime.unsafeRun)
    
    // (for
    //   // cake <- Cake[Int, Int, Int](0, Cdown.LIMIT)
    //   prog = program <*> State.get[Int]
    //   // as <- prog.provideEnvironment(ZEnvironment(cake))
    //   as <- prog.provide(ZLayer.fromZIO(Cake[Int, Int, Int](0, Cdown.LIMIT)))
    // yield as)
    // .pipe(BenchmarkRuntime.unsafeRun)
