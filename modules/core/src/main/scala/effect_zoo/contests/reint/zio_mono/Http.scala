package effect_zoo.contests.reint.zio_mono
import zio._
import effect_zoo.aux.zio_.rws.mono.Reader


trait Http:
  def get(url: String): UIO[String]

object Http:
  def get(url: String): URIO[Has[Http], String] = ZIO.serviceWith[Http](_.get(url))


object MockResponses:
  final case class HttpLive(responseReader: ResponseReader) extends Http:
    override def get(url: String): UIO[String] = responseReader.ask

  object HttpLive:
    val layer: URLayer[Has[ResponseReader], Has[Http]] = (HttpLive(_)).toLayer


object ResponseReader extends Reader[String]

type ResponseReader = ResponseReader.Service
