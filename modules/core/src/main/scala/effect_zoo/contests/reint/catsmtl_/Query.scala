package effect_zoo.contests.reint.catsmtl_
import effect_zoo.contests.Reint.Shared
import cats._
import cats.implicits._


trait Query[F[_]]:
  def listFruits: F[Vector[String]]


def toLoggedHttp[F[_]: Monad](http: Http[F], logging: Logging[F]): Query[F] = new:
  override def listFruits: F[Vector[String]] =
    for
      _ <- logging.logMsg(Shared.MESSAGE)
      response <- http.get(Shared.URL)
      lines = response.split('\n').toVector
    yield lines
