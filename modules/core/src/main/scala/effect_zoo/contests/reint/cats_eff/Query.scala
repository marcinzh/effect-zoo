package effect_zoo.contests.reint.cats_eff
import effect_zoo.contests.Reint.Shared
import cats._
import cats.data._
import cats.implicits._
import org.atnos.eff._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._
import org.atnos.eff.interpret._


enum Query[A]:
  case ListFruits() extends Query[Vector[String]]

object Query:
  def listFruits[U](using Query |= U): Eff[U, Vector[String]] = ListFruits().send


def toLoggedHttp[R, U, A](comp: Eff[R, A])(using Member.Aux[Query, R, U], Http |= U, Logging |= U): Eff[U, A] =
  translate(comp)(new Translate[Query, U]:
    override def apply[X](query: Query[X]): Eff[U, X] =
      query match
        case Query.ListFruits() =>
          for
            _ <- Logging.logMsg(Shared.MESSAGE)
            response <- Http.get(Shared.URL)
            lines = response.split('\n').toVector
          yield lines
  )
