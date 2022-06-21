package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}


object Unfunctional extends Sumh.Entry(Contender.Unfunctional):
  override def round1 =
    val r = Sumh.LIMIT
    var w = 0L
    var s = 0
    
    @scala.annotation.tailrec def loop: Int =
      val a = s
      s = s + 1
      w += a
      if a < r
      then loop
      else a

    try
      val a = loop
      Right(a, w, s)
    catch
      case e: Throwable => Left(e.getMessage())
