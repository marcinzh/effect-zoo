package effect_zoo.contests.reint.zio_layer
import zio._
import effect_zoo.auxx.zio_.rws.layer.Writer


trait Logging:
  def logMsg(text: String): UIO[Unit]

object Logging:
  def logMsg(text: String): URIO[Logging, Unit] = ZIO.serviceWithZIO[Logging](_.logMsg(text))


final case class AccumulateLogMessages(logWriter: LogWriter) extends Logging:
  override def logMsg(text: String): UIO[Unit] = logWriter.tell(Vector(text))

object AccumulateLogMessages:
  val layer: URLayer[LogWriter, Logging] = ZLayer.fromFunction(AccumulateLogMessages(_))


type LogWriter = Writer[Vector[String]]
