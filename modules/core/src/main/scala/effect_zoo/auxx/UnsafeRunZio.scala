package effect_zoo.auxx
import zio._
import zio.{UIO, Runtime, Unsafe}


object UnsafeRunZio:
  extension [A](thiz: UIO[A])
    def unsafeRunZio: A =
      Unsafe.unsafe(implicit _ => Runtime.default.unsafe.run(thiz).getOrThrowFiberFailure())
