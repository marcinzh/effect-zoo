package effect_zoo.auxx.zio_.rws.cake
import zio._


trait State[S]:
  def state: Ref[S]

object State:
  def get[S]: URIO[State[S], S] = ZIO.accessM[State[S]](_.state.get)
  def put[S](s: S): URIO[State[S], Unit] = ZIO.accessM[State[S]](_.state.set(s).unit)
  def update[S](f: S => S): URIO[State[S], Unit] = ZIO.accessM[State[S]](_.state.update(f))
