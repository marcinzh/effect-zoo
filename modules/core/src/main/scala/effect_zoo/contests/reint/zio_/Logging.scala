package effect_zoo.contests.reint.zio_
import zio._


trait Logging:
  def logMsg(text: String): UIO[Unit]

object Logging:
  def logMsg(text: String): URIO[Has[Logging], Unit] = ZIO.serviceWith[Logging](_.logMsg(text))


object AccumulateLogMessages:
  final case class LoggingLive(logWriter: LogWriter) extends Logging:
    override def logMsg(text: String): UIO[Unit] = logWriter.tell(text)

  object LoggingLive:
    def layer: URLayer[Has[LogWriter], Has[Logging]] = (LoggingLive(_)).toLayer


trait LogWriter:
  def tell(text: String): UIO[Unit]
  def hear: UIO[Vector[String]]

object LogWriter:
  def tell(text: String): URIO[Has[LogWriter], Unit] = ZIO.serviceWith[LogWriter](_.tell(text))
  def hear: URIO[Has[LogWriter], Vector[String]] = ZIO.serviceWith[LogWriter](_.hear)
    

final case class LogWriterLive(ref: Ref[Vector[String]]) extends LogWriter:
  override def tell(text: String): UIO[Unit] = ref.update(_ :+ text)
  override def hear: UIO[Vector[String]] = ref.get

object LogWriterLive:
  val layer: ULayer[Has[LogWriter]] = ZLayer.fromEffect(Ref.make(Vector[String]()).map(LogWriterLive(_)))
