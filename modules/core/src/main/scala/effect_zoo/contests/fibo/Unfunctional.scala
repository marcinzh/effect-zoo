package effect_zoo.contests.fibo
import effect_zoo.contests.{Fibo, Contender}


object Unfunctional extends Fibo.Entry(Contender.Unfunctional):
  override def round1 =
    val r = Fibo.LIMIT
    var w = 0
    var s = 0
    
    @scala.annotation.tailrec def loop(a: Int): Int =
      val b = s
      s = a
      val c = a + b
      w += c
      val d = r
      if c < d
      then loop(c)
      else c

    try
      val a = loop(1)
      Right(a, w, s)
    catch
      case e: Throwable => Left(e.getMessage())
