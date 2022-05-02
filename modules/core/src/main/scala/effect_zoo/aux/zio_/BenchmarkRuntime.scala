package effect_zoo.aux.zio_
import zio._

val BenchmarkRuntime = Runtime.default.mapPlatform(_.withTracing(internal.Tracing.disabled))
