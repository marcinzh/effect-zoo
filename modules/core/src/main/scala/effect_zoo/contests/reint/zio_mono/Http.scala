package effect_zoo.contests.reint.zio_mono
import zio._
import effect_zoo.auxx.zio_.rws.mono.Reader


trait Http:
  def get(url: String): UIO[String]

object Http:
  def get(url: String): URIO[Has[Http], String] = ZIO.serviceWith[Http](_.get(url))


final case class MockResponses(responseReader: ResponseReader) extends Http:
  override def get(url: String): UIO[String] = responseReader.ask

object MockResponses:
  val layer: URLayer[Has[ResponseReader], Has[Http]] = (MockResponses(_)).toLayer


object ResponseReader extends Reader[String]

type ResponseReader = ResponseReader.Service
