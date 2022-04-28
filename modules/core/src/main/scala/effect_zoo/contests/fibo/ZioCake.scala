package effect_zoo.contests.fibo
import effect_zoo.contests.{Fibo, Contender}
import scala.util.chaining._
import cats.Monoid
import cats.syntax.semigroup._
import cats.instances.int._
import zio._
import ZioCake_Aux._


object ZioCake extends Fibo.Entry(Contender.ZIO % "Cake"):
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
      aw <- Writer.listen(fibo(1))
      (a, w) = aw
      s <- State.get[Int] 
    yield (a, w, s))
    .pipe(provideCake(Fibo.LIMIT, 0))
    .either
    .pipe(Runtime.default.mapPlatform(_.withTracing(zio.internal.Tracing.disabled)).unsafeRun)


object ZioCake_Aux:
  trait Reader[R] { def reader: Ref[R] }
  trait Writer[W] { def writer: Ref[W] }
  trait State[S] { def state: Ref[S] }

  object Reader:
    def ask[R] = ZIO.accessM[Reader[R]](_.reader.get)

  object Writer:
    def tell[W: Monoid](w: W) = ZIO.accessM[Writer[W]](_.writer.update(_ |+| w).unit)
    def listen[W: Monoid, U <: Writer[W], E, A](body: ZIO[U, E, A]): ZIO[U, E, (A, W)] =
      for
        w0 <- ZIO.accessM[Writer[W]](_.writer.get)
        _ <- ZIO.accessM[Writer[W]](_.writer.set(Monoid[W].empty))
        a <- body
        w1 <- ZIO.accessM[Writer[W]](_.writer.getAndUpdate(w0 |+| _))
      yield (a, w1)

  object State:
    def get[S] = ZIO.accessM[State[S]](_.state.get)
    def put[S](s: S) = ZIO.accessM[State[S]](_.state.set(s).unit)

  def provideCake[R, W: Monoid, S, E, A](r: R, s: S)(comp: ZIO[Reader[R] & Writer[W] & State[S], E, A]) =
    class Cake(
      override val reader: Ref[R],
      override val writer: Ref[W],
      override val state: Ref[S],
    ) extends Reader[R] with Writer[W] with State[S]

    for
      writerSlice <- Ref.make(Monoid[W].empty)
      stateSlice <- Ref.make(s)
      readerSlice <- Ref.make(r)
      cake <- ZIO.succeed(new Cake(readerSlice, writerSlice, stateSlice))
      result <- comp.provide(cake)
    yield result
