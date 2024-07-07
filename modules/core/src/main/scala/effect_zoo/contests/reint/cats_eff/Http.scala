package effect_zoo.contests.reint.cats_eff
import cats._
import cats.data._
import cats.implicits._
import org.atnos.eff._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._
import org.atnos.eff.interpret._


enum Http[A]:
  case Get(url: String) extends Http[String]

object Http:
  def get[U](url: String)(using Http |= U): Eff[U, String] = Get(url).send


def mockResponses[R, U, A](comp: Eff[R, A])(using Member.Aux[Http, R, U], ResponseReader |= U): Eff[U, A] =
  translate(comp):
    new Translate[Http, U]:
      override def apply[X](http: Http[X]): Eff[U, X] =
        http match
          case Http.Get(url) => ask[U, String]


type ResponseReader[A] = Reader[String, A]
