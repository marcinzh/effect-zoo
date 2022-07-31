package effect_zoo.contests.reint.zio_mono
import zio._
import effect_zoo.auxx.zio_.rws.mono.Writer


trait Logging:
  def logMsg(text: String): UIO[Unit]

object Logging:
  def logMsg(text: String): URIO[Has[Logging], Unit] = ZIO.serviceWith[Logging](_.logMsg(text))


final case class AccumulateLogMessages(logWriter: LogWriter) extends Logging:
  override def logMsg(text: String): UIO[Unit] = logWriter.tell(Vector(text))

object AccumulateLogMessages:
  val layer: URLayer[Has[LogWriter], Has[Logging]] = (AccumulateLogMessages(_)).toLayer


object LogWriter extends Writer[Vector[String]]

type LogWriter = LogWriter.Service
