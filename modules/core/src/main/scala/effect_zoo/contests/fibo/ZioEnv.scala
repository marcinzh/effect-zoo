package effect_zoo.contests.fibo
import effect_zoo.contests.{Fibo, Contender}
import scala.util.chaining._
import cats.Monoid
import cats.syntax.semigroup._
import cats.instances.int._
import zio._
import effect_zoo.aux.zio_.BenchmarkRuntime
import effect_zoo.aux.zio_.rws.env.{Reader, Writer, State}


object ZioEnv extends Fibo.Entry(Contender.ZIO % "Env"):
  def fibo(a: Int): ZIO[Reader[Int] & Writer[Int] & State[Int], String, Int] =
    for
      b <- State.get[Int]
      _ <- State.put(a)
      c = a + b
      _ <- Writer.tell(c)
      d <- Reader.ask[Int]
      e <-
        if c < d
        then fibo(c)
        else ZIO.succeed(c)
    yield e


  override def round1 =
    (for
      readerEnv <- Reader.env(Fibo.LIMIT)
      writerEnv <- Writer.env[Int]
      stateEnv <- State.env(0)
      prog = Writer.listen[Int](fibo(1)) <*> State.get[Int]
      aws <- prog.provideEnvironment(readerEnv ++ writerEnv ++ stateEnv)
    yield aws)
    .either
    .pipe(BenchmarkRuntime.unsafeRun)
