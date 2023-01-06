package effect_zoo.auxx.zio_.rws.stateful
import cats.Monoid
import cats.syntax.semigroup._
import zio._


case class ZSWriter[W](w: W)

type Writer[W] = ZState[ZSWriter[W]]

object Writer:
  def tell[W: Tag: Monoid](w: W) = ZIO.updateState[ZSWriter[W]] { case ZSWriter(w0) => ZSWriter(w0 |+| w) }

  def listen[W] = new ListenApply[W]
  class ListenApply[W]:
    def apply[U <: Writer[W], E, A](body: ZIO[U, E, A])(using Tag[W], Monoid[W]): ZIO[U, E, (A, W)] =
      for
        w0 <- ZIO.getStateWith[ZSWriter[W]](_.w)
        _ <- ZIO.setState[ZSWriter[W]](ZSWriter(Monoid[W].empty))
        a <- body
        w1 <- ZIO.getStateWith[ZSWriter[W]](_.w)
        _ <- ZIO.setState[ZSWriter[W]](ZSWriter(w0 |+| w1))
      yield (a, w1)
