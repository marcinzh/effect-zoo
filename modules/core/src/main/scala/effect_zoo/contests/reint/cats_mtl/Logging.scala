package effect_zoo.contests.reint.cats_mtl
import cats.mtl.Tell


trait Logging[F[_]]:
  def logMsg(text: String): F[Unit]


def accumulateLogMessages[F[_]](using W: Tell[F, Vector[String]]): Logging[F] = new:
  override def logMsg(text: String): F[Unit] = W.tell(Vector(text))

