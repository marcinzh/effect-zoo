package effect_zoo.auxx.zio_.rws.cake
import zio._


trait State[S]:
  def state: Ref[S]

object State:
  def get[S: Tag]: URIO[State[S], S] = ZIO.serviceWithZIO[State[S]](_.state.get)
  def put[S: Tag](s: S): URIO[State[S], Unit] = ZIO.serviceWithZIO[State[S]](_.state.set(s).unit)
  def update[S: Tag](f: S => S): URIO[State[S], Unit] = ZIO.serviceWithZIO[State[S]](_.state.update(f))
