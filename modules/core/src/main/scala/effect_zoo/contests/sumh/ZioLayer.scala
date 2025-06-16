package effect_zoo.contests.sumh
import effect_zoo.contests.{Sumh, Contender}
import cats.Monoid
import cats.instances.int._
import zio._
import effect_zoo.auxx.UnsafeRunZio._
import effect_zoo.auxx.zio_.rws.layer.{Reader, ReaderLive, Writer, WriterLive, State, StateLive}


object ZioLayer extends Sumh.Entry(Contender.ZIO % "Layer"):
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
    (Writer.listen[Long](prog) <*> State.get[Int])
    .provideLayer(
      StateLive.layer(0) ++
      WriterLive.layer[Long] ++
      ReaderLive.layer(Sumh.LIMIT)
    )
    .either
    .unsafeRunZio
