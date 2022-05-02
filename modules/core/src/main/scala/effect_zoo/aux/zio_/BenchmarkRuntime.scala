package effect_zoo.aux.zio_
import zio._

val BenchmarkRuntime = Runtime.default.mapRuntimeConfig(_ => RuntimeConfig.benchmark)
