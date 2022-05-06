package effect_zoo.contests.reint.zio_mono
import effect_zoo.contests.Reint.Shared
import zio._


trait Query:
  def listFruits: UIO[Vector[String]]

object Query:
  def listFruits: URIO[Has[Query], Vector[String]] = ZIO.serviceWith[Query](_.listFruits)


object ToLoggedHttp:
  final case class QueryLive(http: Http, logging: Logging) extends Query:
    override def listFruits: UIO[Vector[String]] =
      for
        _ <- logging.logMsg(Shared.MESSAGE)
        response <- http.get(Shared.URL)
        lines = response.split('\n').toVector
      yield lines

  object QueryLive:
    val layer: URLayer[Has[Http] & Has[Logging], Has[Query]] = (QueryLive(_, _)).toLayer
