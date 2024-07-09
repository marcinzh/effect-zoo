package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import scala.util.chaining._
import zio.prelude.fx.ZPure
import zio.ZEnvironment


object Zpure_Desugared extends Sumh.Entry(Contender.ZPure % "Desugared"):
  def prog: ZPure[Long, Int, Int, Int, String, Int] =
    ZPure.get.flatMap: s =>
      ZPure.set(s + 1).flatMap: _ =>
        ZPure.log(s.toLong).flatMap: _ =>
          ZPure.serviceWith[Int](x => x).flatMap: r =>
            if s < r
            then prog
            else ZPure.succeed(s)

  override def round1 =
    prog
    .provideEnvironment(ZEnvironment(Sumh.LIMIT))
    .runAll(0)
    .pipe { case (ww, ee) =>
      val w = ww.foldLeft(0L)(_ + _)
      ee.map { case (s, a) => (a, w, s) }
      .left.map(_.toString)
    }
