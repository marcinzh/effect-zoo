package effect_zoo.auxx.zio_.rws.cake
import cats.Monoid
import cats.syntax.semigroup._
import zio._


trait Writer[W]:
  def writer: Ref[W]

object Writer:
  def tell[W: Tag: Monoid](w: W) = ZIO.serviceWithZIO[Writer[W]](_.writer.update(_ |+| w).unit)
  def listen[W] = new ListenApply[W]
  class ListenApply[W]:
    def apply[U <: Writer[W], E, A](body: ZIO[U, E, A])(using Tag[W], Monoid[W]): ZIO[U, E, (A, W)] =
      for
        w0 <- ZIO.serviceWithZIO[Writer[W]](_.writer.get)
        _ <- ZIO.serviceWithZIO[Writer[W]](_.writer.set(Monoid[W].empty))
        a <- body
        w1 <- ZIO.serviceWithZIO[Writer[W]](_.writer.getAndUpdate(w0 |+| _))
      yield (a, w1)
      