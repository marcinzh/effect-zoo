package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}
import zio._
import effect_zoo.auxx.UnsafeRunZio._


object ZioRef extends Cdown.Entry(Contender.ZIO % "Ref"):
  def program: URIO[Ref[Int], Int] =
    ZIO.serviceWithZIO: ref =>
      ref.get.flatMap: n =>
        if n <= 0
        then ZIO.succeed(n)
        else ref.set(n - 1) *> program

  override def round1 =
    (for
      ref <- Ref.make(Cdown.LIMIT)
      prog = program <*> ref.get
      as <- prog.provideEnvironment(ZEnvironment(ref))
    yield as)
    .unsafeRunZio
