package effect_zoo.contests.reint.cats_mtl
import cats.mtl.Ask


trait Http[F[_]]:
  def get(url: String): F[String]


def mockResponses[F[_]](using R: Ask[F, String]): Http[F] = new:
  override def get(url: String): F[String] = R.ask
