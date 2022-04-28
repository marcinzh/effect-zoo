package effect_zoo.contests.fibo
import effect_zoo.contests.{Fibo, Contender}
import scala.util.chaining._
import zio.prelude.fx.ZPure


object Zpure extends Fibo.Entry(Contender.ZPure):
  def fibo(a: Int): ZPure[Int, Int, Int, Int, String, Int] =
    for
      b <- ZPure.get
      _ <- ZPure.set(a)
      c = a + b
      _ <- ZPure.log(c)
      d <- ZPure.access[Int](x => x)
      e <-
        if c < d
        then fibo(c)
        else ZPure.succeed(c)
    yield e

  override def round1 =
    fibo(1)
    .provide(Fibo.LIMIT)
    .runAll(0)
    .pipe { case (ww, ee) =>
      val w = ww.foldLeft(0)(_ + _)
      ee.map { case (s, a) => (a, w, s) }
      .left.map(_.toString)
    }
