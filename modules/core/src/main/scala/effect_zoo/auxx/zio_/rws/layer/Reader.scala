package effect_zoo.auxx.zio_.rws.layer
import zio._


trait Reader[R]:
  def ask: UIO[R]

object Reader:
  def ask[R: Tag]: URIO[Reader[R], R] = ZIO.serviceWithZIO[Reader[R]](_.ask)


case class ReaderLive[R: Tag](ref: Ref[R]) extends Reader[R]:
  override def ask: UIO[R] = ref.get

object ReaderLive:
  def layer[R: Tag](r: R): ULayer[Reader[R]] = ZLayer.fromZIO(Ref.make[R](r).map(ReaderLive.apply))
