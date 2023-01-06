package effect_zoo.auxx.zio_.rws.layer
import cats.Monoid
import cats.syntax.semigroup._
import zio._


trait Writer[W]:
  def tell(w: W): UIO[Unit]
  def listen[U, E, A](body: ZIO[U, E, A]): ZIO[U, E, (A, W)]

object Writer:
  def tell[W: Tag](w: W): URIO[Writer[W], Unit] = ZIO.serviceWithZIO[Writer[W]](_.tell(w))
  def listen[W] = new ListenApply[W]
  class ListenApply[W]:
    def apply[U <: Writer[W], E, A](body: ZIO[U, E, A])(using Tag[W]): ZIO[U, E, (A, W)] = ZIO.serviceWithZIO[Writer[W]](_.listen(body))


case class WriterLive[W: Tag: Monoid](ref: Ref[W]) extends Writer[W]:
  override def tell(w: W): UIO[Unit] = ref.update(_ |+| w)
  override def listen[U, E, A](body: ZIO[U, E, A]): ZIO[U, E, (A, W)] =
    for
      w0 <- ref.getAndSet(Monoid[W].empty)
      a <- body
      w1 <- ref.getAndUpdate(w0 |+| _)
    yield (a, w1)

object WriterLive:
  def layer[W: Tag: Monoid]: ULayer[Writer[W]] = ZLayer.fromZIO(Ref.make[W](Monoid.empty).map(WriterLive.apply))
