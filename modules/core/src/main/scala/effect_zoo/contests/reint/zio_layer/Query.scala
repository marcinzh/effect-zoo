package effect_zoo.contests.reint.zio_layer
import effect_zoo.contests.Reint.Shared
import zio._


trait Query:
  def listFruits: UIO[Vector[String]]

object Query:
  def listFruits: URIO[Query, Vector[String]] = ZIO.serviceWithZIO[Query](_.listFruits)


final case class ToLoggedHttp(http: Http, logging: Logging) extends Query:
  override def listFruits: UIO[Vector[String]] =
    for
      _ <- logging.logMsg(Shared.MESSAGE)
      response <- http.get(Shared.URL)
      lines = response.split('\n').toVector
    yield lines

object ToLoggedHttp:
  val layer: URLayer[Http & Logging, Query] = ZLayer.fromFunction(ToLoggedHttp.apply _)
