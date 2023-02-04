package effect_zoo.contests.cdown

import language.implicitConversions
import effect_zoo.contests.{Cdown, Contender}
import kyo.core._
import kyo.ios._
import kyo.concurrent.atomics._

object Kyo extends Cdown.Entry(Contender.Kyo):
  def program(a: AtomicInteger): Int > IOs =
    a.decrementAndGet {
      case 0 => 0
      case _ => program(a)
    }

  override def round1 =
    IOs.run {
      for {
        a <- AtomicInteger(Cdown.LIMIT)
        v <- program(a)
      } yield (0, v)
    }
