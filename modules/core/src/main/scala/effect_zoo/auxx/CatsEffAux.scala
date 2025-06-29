package effect_zoo.auxx
import org.atnos.eff.Eff

object CatsEffAux:
  // In Cats-Eff both `*>` and `>>` pass the rhs arg by value
  extension [A, U](thiz: Eff[U, A])
    inline def *>>[B](inline that: => Eff[U, B]): Eff[U, B] = thiz.flatMap(_ => that)
