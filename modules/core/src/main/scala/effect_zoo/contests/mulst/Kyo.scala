package effect_zoo.contests.mulst

import kyo.core._
import kyo.ios._
import kyo.concurrent.atomics._
import effect_zoo.contests.Mulst
import effect_zoo.registry.Contender
import language.implicitConversions

object Kyo extends Mulst.Entry(Contender.Kyo):

  case class Atomics(
      a1: AtomicInteger,
      a2: AtomicInteger,
      a3: AtomicInteger,
      a4: AtomicInteger,
      a5: AtomicInteger
  )

  val atomics: Atomics > IOs =
    for {
      a1 <- AtomicInteger(0)
      a2 <- AtomicInteger(0)
      a3 <- AtomicInteger(0)
      a4 <- AtomicInteger(0)
      a5 <- AtomicInteger(0)
    } yield new Atomics(a1, a2, a3, a4, a5)

  override def round1 = {
    def prog(n: Int, atomics: Atomics): Unit > IOs = {
      import atomics._
      if (n <= 0) {
        IOs.unit
      } else {
        val update =
          for {
            _ <- a1.addAndGet(1)
            _ <- a1.addAndGet(10)
            _ <- a1.addAndGet(100)
            _ <- a1.addAndGet(1000)
            _ <- a1.addAndGet(10000)
          } yield ()
        update(_ => prog(n - 1, atomics))
      }
    }
    IOs.run {
      for {
        a <- atomics
        _ <- prog(Mulst.LIMIT, a)
        v1 <- a.a1.get
      } yield v1
    }
  }

  override def round2 = {
    def prog(n: Int, atomics: Atomics): Unit > IOs = {
      import atomics._
      if (n <= 0) {
        IOs.unit
      } else {
        val update =
          for {
            _ <- a1.addAndGet(1)
            _ <- a2.addAndGet(10)
            _ <- a1.addAndGet(100)
            _ <- a2.addAndGet(1000)
            _ <- a1.addAndGet(10000)
          } yield ()
        update(_ => prog(n - 1, atomics))
      }
    }
    IOs.run {
      for {
        a <- atomics
        _ <- prog(Mulst.LIMIT, a)
        v1 <- a.a1.get
        v2 <- a.a2.get
      } yield (v1, v2)
    }
  }

  override def round3 = {
    def prog(n: Int, atomics: Atomics): Unit > IOs = {
      import atomics._
      if (n <= 0) {
        IOs.unit
      } else {
        val update =
          for {
            _ <- a1.addAndGet(1)
            _ <- a2.addAndGet(10)
            _ <- a3.addAndGet(100)
            _ <- a1.addAndGet(1000)
            _ <- a2.addAndGet(10000)
          } yield ()
        update(_ => prog(n - 1, atomics))
      }
    }
    IOs.run {
      for {
        a <- atomics
        _ <- prog(Mulst.LIMIT, a)
        v1 <- a.a1.get
        v2 <- a.a2.get
        v3 <- a.a3.get
      } yield (v1, v2, v3)
    }
  }

  override def round4 = {
    def prog(n: Int, atomics: Atomics): Unit > IOs = {
      import atomics._
      if (n <= 0) {
        IOs.unit
      } else {
        val update =
          for {
            _ <- a1.addAndGet(1)
            _ <- a2.addAndGet(10)
            _ <- a3.addAndGet(100)
            _ <- a4.addAndGet(1000)
            _ <- a1.addAndGet(10000)
          } yield ()
        update(_ => prog(n - 1, atomics))
      }
    }
    IOs.run {
      for {
        a <- atomics
        _ <- prog(Mulst.LIMIT, a)
        v1 <- a.a1.get
        v2 <- a.a2.get
        v3 <- a.a3.get
        v4 <- a.a4.get
      } yield (v1, v2, v3, v4)
    }
  }

  override def round5 = {
    def prog(n: Int, atomics: Atomics): Unit > IOs = {
      import atomics._
      if (n <= 0) {
        IOs.unit
      } else {
        val update =
          for {
            _ <- a1.addAndGet(1)
            _ <- a2.addAndGet(10)
            _ <- a3.addAndGet(100)
            _ <- a4.addAndGet(1000)
            _ <- a5.addAndGet(10000)
          } yield ()
        update(_ => prog(n - 1, atomics))
      }
    }
    IOs.run {
      for {
        a <- atomics
        _ <- prog(Mulst.LIMIT, a)
        v1 <- a.a1.get
        v2 <- a.a2.get
        v3 <- a.a3.get
        v4 <- a.a4.get
        v5 <- a.a5.get
      } yield (v1, v2, v3, v4, v5)
    }
  }
