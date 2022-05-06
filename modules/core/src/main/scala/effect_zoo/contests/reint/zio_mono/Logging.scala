package effect_zoo.contests.reint.zio_mono
import zio._
import effect_zoo.aux.zio_.rws.mono.Writer


trait Logging:
  def logMsg(text: String): UIO[Unit]

object Logging:
  def logMsg(text: String): URIO[Has[Logging], Unit] = ZIO.serviceWith[Logging](_.logMsg(text))


object AccumulateLogMessages:
  final case class LoggingLive(logWriter: LogWriter) extends Logging:
    override def logMsg(text: String): UIO[Unit] = logWriter.tell(Vector(text))

  object LoggingLive:
    val layer: URLayer[Has[LogWriter], Has[Logging]] = (LoggingLive(_)).toLayer


object LogWriter extends Writer[Vector[String]]

type LogWriter = LogWriter.Service
