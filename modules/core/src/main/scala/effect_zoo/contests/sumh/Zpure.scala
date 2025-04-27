package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import scala.util.chaining._
import zio.prelude.fx.ZPure
import zio.ZEnvironment


object Zpure extends Sumh.Entry(Contender.ZPure):
  def prog: ZPure[Long, Int, Int, Int, String, Int] =
    for
      s <- ZPure.get
      _ <- ZPure.set(s + 1)
      _ <- ZPure.log(s.toLong)
      r <- ZPure.service[Int, Int]
      x <-
        if s < r
        then prog
        else ZPure.succeed(s)
    yield x

  override def round1 =
    prog
    .provideEnvironment(ZEnvironment(Sumh.LIMIT))
    .runAll(0)
    .pipe { case (ww, ee) =>
      val w = ww.foldLeft(0L)(_ + _)
      ee.map { case (s, a) => (a, w, s) }
      .left.map(_.toString)
    }
