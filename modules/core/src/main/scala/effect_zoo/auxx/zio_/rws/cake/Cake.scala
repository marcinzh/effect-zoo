package effect_zoo.auxx.zio_.rws.cake
import cats.Monoid
import zio._


class Cake[R, W, S] private (
  override val reader: Ref[R],
  override val writer: Ref[W],
  override val state: Ref[S],
) extends Reader[R] with Writer[W] with State[S]


object Cake:
  def apply[R, W: Monoid, S](r: R, s: S): UIO[Cake[R, W, S]] =
    for
      writerSlice <- Ref.make(Monoid[W].empty)
      stateSlice <- Ref.make(s)
      readerSlice <- Ref.make(r)
      cake <- ZIO.succeed(new Cake(readerSlice, writerSlice, stateSlice))
    yield cake
