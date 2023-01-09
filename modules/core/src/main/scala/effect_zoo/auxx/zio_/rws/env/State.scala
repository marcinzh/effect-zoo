package effect_zoo.auxx.zio_.rws.env
import zio._


case class State[S](ref: Ref[S])

object State:
  def get[S: Tag]: URIO[State[S], S] = ZIO.environmentWithZIO[State[S]](_.get.ref.get)
  def put[S: Tag](s: S): URIO[State[S], Unit] = ZIO.environmentWithZIO[State[S]](_.get.ref.set(s))
  def update[S: Tag](f: S => S): URIO[State[S], Unit] = ZIO.environmentWithZIO[State[S]](_.get.ref.update(f))

  def env[S: Tag](initial: S): UIO[ZEnvironment[State[S]]] =
    for
      ref <- Ref.make(initial)
      e <- ZIO.succeed(State[S](ref))
    yield ZEnvironment(e)
