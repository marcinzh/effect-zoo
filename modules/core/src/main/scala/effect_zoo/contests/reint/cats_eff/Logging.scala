package effect_zoo.contests.reint.cats_eff
import cats._
import cats.data._
import cats.implicits._
import org.atnos.eff._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._
import org.atnos.eff.interpret._


enum Logging[A]:
  case LogMsg(url: String) extends Logging[Unit]

object Logging:
  def logMsg[U](text: String)(using Logging |= U): Eff[U, Unit] = LogMsg(text).send


def accumulateLogMessages[R, U, A](comp: Eff[R, A])(using Member.Aux[Logging, R, U], LogWriter |= U): Eff[U, A] =
  translate(comp)(new Translate[Logging, U]:
    override def apply[X](logging: Logging[X]): Eff[U, X] =
      logging match
        case Logging.LogMsg(text) => tell(Vector(text))
  )


type LogWriter[A] = Writer[Vector[String], A]
