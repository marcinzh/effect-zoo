package effect_zoo.contests.reint.zio_layer
import zio._
import effect_zoo.aux.zio_.rws.layer.Reader


trait Http:
  def get(url: String): UIO[String]

object Http:
  def get(url: String): URIO[Http, String] = ZIO.serviceWithZIO[Http](_.get(url))


object MockResponses:
  final case class HttpLive(responseReader: ResponseReader) extends Http:
    override def get(url: String): UIO[String] = responseReader.ask

  object HttpLive:
    val layer: URLayer[ResponseReader, Http] = ZLayer.fromFunction(HttpLive.apply _)


type ResponseReader = Reader[String]
