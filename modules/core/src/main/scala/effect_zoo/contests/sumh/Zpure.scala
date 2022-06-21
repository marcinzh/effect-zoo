package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import scala.util.chaining._
import zio.prelude.fx.ZPure


object Zpure extends Sumh.Entry(Contender.ZPure):
  def prog: ZPure[Long, Int, Int, Int, String, Int] =
    for
      s <- ZPure.get
      _ <- ZPure.set(s + 1)
      _ <- ZPure.log(s.toLong)
      r <- ZPure.access[Int](x => x)
      x <-
        if s < r
        then prog
        else ZPure.succeed(s)
    yield x

  override def round1 =
    prog
    .provide(Sumh.LIMIT)
    .runAll(0)
    .pipe { case (ww, ee) =>
      val w = ww.foldLeft(0L)(_ + _)
      ee.map { case (s, a) => (a, w, s) }
      .left.map(_.toString)
    }
