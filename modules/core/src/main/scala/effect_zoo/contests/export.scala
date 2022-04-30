package effect_zoo.contests

export effect_zoo.registry.Contender

val ZioBenchmarkRuntime = zio.Runtime.default.mapPlatform(_.withTracing(zio.internal.Tracing.disabled))
