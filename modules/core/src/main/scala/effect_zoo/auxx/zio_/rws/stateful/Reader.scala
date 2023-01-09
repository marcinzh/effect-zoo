package effect_zoo.auxx.zio_.rws.stateful
import zio._


case class ZSReader[R](r: R)

type Reader[R] = ZState[ZSReader[R]]

object Reader:
  def ask[R: Tag] = ZIO.getStateWith[ZSReader[R]](_.r)
