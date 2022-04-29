package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import scala.util.chaining._
import zio._
import Zio_Aux._


object Zio extends Cdown.Entry(Contender.ZIO):
  def program: URIO[State[Int], Int] =
    State.get[Int].flatMap { n =>
      if n <= 0
      then ZIO.succeed(n)
      else State.put(n - 1) *> program
    }

  override def round1 =
    State.run(program, Cdown.LIMIT)
    .pipe(Runtime.default.mapPlatform(_.withTracing(zio.internal.Tracing.disabled)).unsafeRun)


object Zio_Aux:
  trait State[S] { def state: Ref[S] }

  object State:
    def get[S] = ZIO.accessM[State[S]](_.state.get)
    def put[S](s: S) = ZIO.accessM[State[S]](_.state.set(s).unit)

    def run[S, E, A](prog: ZIO[State[S], E, A], initial: S): ZIO[Any, E, (A, S)] =
      for
        ref <- Ref.make(initial)
        env <- ZIO.succeed(new State[S] { def state = ref })
        prog2 = prog <*> State.get[S]
        a_s <- prog2.provide(env)
      yield a_s

