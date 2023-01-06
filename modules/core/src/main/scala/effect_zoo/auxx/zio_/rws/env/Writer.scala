package effect_zoo.auxx.zio_.rws.env
import cats.Monoid
import cats.syntax.semigroup._
import zio._


case class Writer[W](ref: Ref[W])

object Writer:
  def tell[W: Tag: Monoid](w: W): URIO[Writer[W], Unit] = ZIO.environmentWithZIO[Writer[W]](_.get.ref.update(_ |+| w))

  def listen[W] = new ListenApply[W]
  class ListenApply[W]:
    def apply[U <: Writer[W], E, A](body: ZIO[U, E, A])(using Tag[W], Monoid[W]): ZIO[U, E, (A, W)] =
      for
        ref <- ZIO.environmentWith[Writer[W]](_.get.ref)
        w1 <- ref.getAndSet(Monoid[W].empty)
        a <- body
        w2 <- ref.getAndUpdate(w1 |+| _)
      yield (a, w2)

  def env[W: Tag: Monoid]: UIO[ZEnvironment[Writer[W]]] =
    for
      ref <- Ref.make(Monoid[W].empty)
      e <- ZIO.succeed(Writer[W](ref))
    yield ZEnvironment(e)
