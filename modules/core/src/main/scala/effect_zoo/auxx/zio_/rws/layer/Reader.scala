package effect_zoo.auxx.zio_.rws.layer
import zio._


trait Reader[R]:
  def ask: UIO[R]

object Reader:
  def ask[R: Tag]: URIO[Has[Reader[R]], R] = ZIO.serviceWith[Reader[R]](_.ask)


case class ReaderLive[R: Tag](ref: Ref[R]) extends Reader[R]:
  override def ask: UIO[R] = ref.get

object ReaderLive:
  def layer[R: Tag](r: R): ULayer[Has[Reader[R]]] = ZLayer.fromEffect(Ref.make[R](r).map(ReaderLive.apply))
