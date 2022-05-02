package effect_zoo.contests.reint.zio_layer
import effect_zoo.contests.Reint.Shared
import zio._


trait Query:
  def listFruits: UIO[Vector[String]]

object Query:
  def listFruits: URIO[Query, Vector[String]] = ZIO.serviceWithZIO[Query](_.listFruits)


object ToLoggedHttp:
  final case class QueryLive(http: Http, logging: Logging) extends Query:
    override def listFruits: UIO[Vector[String]] =
      for
        _ <- logging.logMsg(Shared.MESSAGE)
        response <- http.get(Shared.URL)
        lines = response.split('\n').toVector
      yield lines

  object QueryLive:
    // val layer: URLayer[Http & Logging, Query] = ZLayer.fromFunction(QueryLive.apply _)
    val layer: URLayer[Http & Logging, Query] = ZLayer.fromFunction[Http, Logging, Query](QueryLive.apply _)
