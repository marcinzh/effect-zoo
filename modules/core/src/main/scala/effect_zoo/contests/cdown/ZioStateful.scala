package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import scala.util.chaining._
import zio._
import effect_zoo.auxx.UnsafeRunZio._
import effect_zoo.auxx.zio_.rws.stateful.{State, ZSState}


object ZioStateful extends Cdown.Entry(Contender.ZIO % "Stateful"):
  def program: URIO[State[Int], Int] =
    State.get[Int].flatMap: n =>
      if n <= 0
      then ZIO.succeed(n)
      else State.put(n - 1) *> program

  override def round1 =
    (program <*> State.get[Int])
    .pipe(ZIO.stateful[Any](ZSState(Cdown.LIMIT)))
    .unsafeRunZio
