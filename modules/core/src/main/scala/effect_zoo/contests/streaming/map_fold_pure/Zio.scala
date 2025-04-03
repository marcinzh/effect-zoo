package effect_zoo.contests.streaming.map_fold_pure
import effect_zoo.contests.streaming.{MapFoldPure, Contender}
import scala.util.chaining._
import zio._
import zio.stream._
import effect_zoo.auxx.zio_.BenchmarkRuntime


object Zio extends MapFoldPure.Entry(Contender.ZIO):
  override def round1 =
    ZStream
      .fromIterable(MapFoldPure.theSeq)
      .filter(x => x % 2 == 0)
      .map(x => x + 1)
      .runSum
      .pipe(BenchmarkRuntime.unsafeRun)
