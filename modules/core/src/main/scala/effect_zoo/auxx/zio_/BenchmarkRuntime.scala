package effect_zoo.auxx.zio_
import zio._
import zio.{UIO, Runtime, Unsafe}


object BenchmarkRuntime:
  // No longer possible to disable tracing? :(
  def unsafeRun[A](prog: UIO[A]): A =
    Unsafe.unsafe(implicit _ => Runtime.default.unsafe.run(prog).getOrThrowFiberFailure())
