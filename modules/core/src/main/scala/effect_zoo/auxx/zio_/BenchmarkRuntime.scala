package effect_zoo.auxx.zio_
import zio._

val BenchmarkRuntime = Runtime.default.mapPlatform(_.withTracing(internal.Tracing.disabled))
