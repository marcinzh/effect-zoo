package effect_zoo.diy


object Measure:
  final case class Config(
    probeLimitMillis: Int = 200,
    measureLimitMillis: Int = 3000,
  )

  def apply(stuff: => Any)(implicit config: Config): Double =
    val millisToNanos = 1000L * 1000L
    measure(
      () => stuff,
      probeLimitNanos = config.probeLimitMillis * millisToNanos,
      measureLimitNanos = config.measureLimitMillis * millisToNanos,
    )

  private def measure(fun: () => Any, probeLimitNanos: Long, measureLimitNanos: Long): Double =
    System.gc()
    val reps = probe(fun, probeLimitNanos)
    System.gc()
    val t0 = System.nanoTime()
    def loop(iter: Long): Double =
      val result = repeat(fun, reps)
      val t = System.nanoTime() - t0
      if t < measureLimitNanos
      then loop(iter + 1)
      else reps.toDouble * iter.toDouble * 1000.0 * 1000.0 * 1000.0 / t
    loop(1)

  private def probe(fun: () => Any, limitNanos: Long): Long =
    def loop(reps: Long): Long =
      val t0 = System.nanoTime()
      val result = repeat(fun, reps)
      val t = System.nanoTime() - t0
      if t < limitNanos
      then loop(reps * 2)
      else reps
    loop(1)

  private def repeat(fun: () => Any, reps: Long): Unit =
    var n = reps
    while n > 0 do
      fun()
      n -= 1
