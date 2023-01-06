package effect_zoo.auxx.zio_.rws.stateful
import zio._


case class ZSState[S](s: S)

type State[S] = ZState[ZSState[S]]

object State:
  def get[S: Tag] = ZIO.getStateWith[ZSState[S]](_.s)
  def put[S: Tag](s: S) = ZIO.setState[ZSState[S]](ZSState(s))
  def update[S: Tag](f: S => S) = ZIO.updateState[ZSState[S]] { case ZSState(s) => ZSState(f(s)) }
