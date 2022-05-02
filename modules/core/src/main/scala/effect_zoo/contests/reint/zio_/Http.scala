package effect_zoo.contests.reint.zio_
import zio._


trait Http:
  def get(url: String): UIO[String]

object Http:
  def get(url: String): URIO[Has[Http], String] = ZIO.serviceWith[Http](_.get(url))


object MockResponses:
  final case class HttpLive(responseReader: ResponseReader) extends Http:
    override def get(url: String): UIO[String] = responseReader.ask

  object HttpLive:
    val layer: URLayer[Has[ResponseReader], Has[Http]] = (HttpLive(_)).toLayer


trait ResponseReader:
  def ask: UIO[String]

object ResponseReader:
  def ask: URIO[Has[ResponseReader], String] = ZIO.serviceWith[ResponseReader](_.ask)

final case class ResponseReaderLive(value: String) extends ResponseReader:
  override def ask: UIO[String] = ZIO.succeed(value)

object ResponseReaderLive:
  def layer(value: String): ULayer[Has[ResponseReader]] = ZLayer.succeed(ResponseReaderLive(value))


