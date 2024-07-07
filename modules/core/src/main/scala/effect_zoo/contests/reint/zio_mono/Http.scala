package effect_zoo.contests.reint.zio_mono
import zio._
import effect_zoo.auxx.zio_.rws.mono.Reader


trait Http:
  def get(url: String): UIO[String]

object Http:
  def get(url: String): URIO[Http, String] = ZIO.serviceWithZIO[Http](_.get(url))


final case class MockResponses(responseReader: ResponseReader) extends Http:
  override def get(url: String): UIO[String] = responseReader.ask

object MockResponses:
  val layer: URLayer[ResponseReader, Http] = ZLayer.fromFunction(MockResponses(_))


object ResponseReader extends Reader[String]

type ResponseReader = ResponseReader.Service
