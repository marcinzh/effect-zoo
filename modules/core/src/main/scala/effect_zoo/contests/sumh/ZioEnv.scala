package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import cats.Monoid
import cats.syntax.semigroup._
import cats.instances.int._
import zio._
import effect_zoo.auxx.UnsafeRunZio._
import effect_zoo.auxx.zio_.rws.env.{Reader, Writer, State}


object ZioEnv extends Sumh.Entry(Contender.ZIO % "Env"):
  def prog: ZIO[Reader[Int] & Writer[Long] & State[Int], String, Int] =
    for
      s <- State.get[Int]
      _ <- State.put(s + 1)
      _ <- Writer.tell(s.toLong)
      r <- Reader.ask[Int]
      x <-
        if s < r
        then prog
        else ZIO.succeed(s)
    yield x


  override def round1 =
    (for
      readerEnv <- Reader.env(Sumh.LIMIT)
      writerEnv <- Writer.env[Long]
      stateEnv <- State.env(0)
      prog2 = Writer.listen[Long](prog) <*> State.get[Int]
      aws <- prog2.provideEnvironment(readerEnv ++ writerEnv ++ stateEnv)
    yield aws)
    .either
    .unsafeRunZio
