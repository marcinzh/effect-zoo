/*******************************/
/* Generated by `sbt meta/run` */
/*******************************/
package effect_zoo.bench.contests
import java.util.concurrent.TimeUnit
import org.openjdk.jmh.annotations._
import effect_zoo.registry.Registry

@State(Scope.Thread)
@BenchmarkMode(Array(Mode.Throughput))
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(jvmArgs = Array("-Dcats.effect.tracing.mode=DISABLED", "-Xms2g", "-Xmx2g"))
class Mulst {
  val reg = Registry.findByContestName("Mulst")

  val CatsCore__0__run = reg.findRound("CatsCore", 0).run
  val CatsCore__1__run = reg.findRound("CatsCore", 1).run
  val CatsCore__2__run = reg.findRound("CatsCore", 2).run
  val CatsCore__3__run = reg.findRound("CatsCore", 3).run
  val CatsCore__4__run = reg.findRound("CatsCore", 4).run
  val Turbolift__0__run = reg.findRound("Turbolift", 0).run
  val Turbolift__1__run = reg.findRound("Turbolift", 1).run
  val Turbolift__2__run = reg.findRound("Turbolift", 2).run
  val Turbolift__3__run = reg.findRound("Turbolift", 3).run
  val Turbolift__4__run = reg.findRound("Turbolift", 4).run
  
  @Benchmark def CatsCore__0 = CatsCore__0__run()
  @Benchmark def CatsCore__1 = CatsCore__1__run()
  @Benchmark def CatsCore__2 = CatsCore__2__run()
  @Benchmark def CatsCore__3 = CatsCore__3__run()
  @Benchmark def CatsCore__4 = CatsCore__4__run()
  @Benchmark def Turbolift__0 = Turbolift__0__run()
  @Benchmark def Turbolift__1 = Turbolift__1__run()
  @Benchmark def Turbolift__2 = Turbolift__2__run()
  @Benchmark def Turbolift__3 = Turbolift__3__run()
  @Benchmark def Turbolift__4 = Turbolift__4__run()
}
