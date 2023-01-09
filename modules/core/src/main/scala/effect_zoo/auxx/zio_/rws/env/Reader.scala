package effect_zoo.auxx.zio_.rws.env
import zio._


case class Reader[R](ref: Ref[R])

object Reader:
  def ask[R: Tag]: URIO[Reader[R], R] = ZIO.environmentWithZIO[Reader[R]](_.get.ref.get)

  def env[R: Tag](initial: R): UIO[ZEnvironment[Reader[R]]] =
    for
      ref <- Ref.make(initial)
      e <- ZIO.succeed(Reader[R](ref))
    yield ZEnvironment(e)
