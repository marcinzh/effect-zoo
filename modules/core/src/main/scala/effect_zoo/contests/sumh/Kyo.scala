package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import kyo.*
import kyo.kernel.`<`.*
import scala.util.chaining._


object Kyo extends Sumh.Entry(Contender.Kyo):
  def prog: Int < (Abort[String] & Env[Int] & Var[Int] & Var[Long]) =
    Var.use[Int]: s =>
      Var
        .set(s + 1)
        .andThen(Var.update[Long](_ + s.toLong))
        .andThen(
          Env.use[Int]: r =>
            if s < r then (prog) else s 
        )

  override def round1 =
    prog.handle(
      Var.runTuple(0),
      Var.runTuple(0L),
      Env.run(Sumh.LIMIT),
      _.map { case (long, (int, r)) => (r, long, int) },
      Abort.run(_)
    )
    .eval.toEither.left.map(_.toString)
