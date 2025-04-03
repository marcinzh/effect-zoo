package effect_zoo.contests.cdown
import effect_zoo.contests.{Cdown, Contender}


object Unfunctional extends Cdown.Entry(Contender.Unfunctional):
  override def round1 =
    var s = Cdown.LIMIT

    @scala.annotation.tailrec def loop: Int =
      if s <= 0
      then s
      else s = s - 1; loop

    val a = loop
    (a, s)
