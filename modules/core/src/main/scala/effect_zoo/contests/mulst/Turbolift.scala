package effect_zoo.contests.mulst
import effect_zoo.contests.{Contender, Mulst}
import scala.util.chaining._
import turbolift.!!
import turbolift.std_effects.State


object Turbolift extends Mulst.Entry(Contender.Turbolift):
  case object MyState1 extends State[Int]
  case object MyState2 extends State[Int]
  case object MyState3 extends State[Int]
  case object MyState4 extends State[Int]
  case object MyState5 extends State[Int]
  type MyState1 = MyState1.type
  type MyState2 = MyState2.type
  type MyState3 = MyState3.type
  type MyState4 = MyState4.type
  type MyState5 = MyState5.type


  override def round1 =
    def prog(n: Int): Unit !! MyState1 =
      if n <= 0
      then !!.unit
      else
        MyState1.modify(_ + 1) &&!
        MyState1.modify(_ + 10) &&!
        MyState1.modify(_ + 100) &&!
        MyState1.modify(_ + 1000) &&!
        MyState1.modify(_ + 10000) &&!
        prog(n - 1)

    prog(Mulst.LIMIT)
    .runWith(MyState1.handler(0).exec)


  override def round2 =
    def prog(n: Int): Unit !! (MyState1 & MyState2) =
      if n <= 0
      then !!.unit
      else
        MyState1.modify(_ + 1) &&!
        MyState2.modify(_ + 10) &&!
        MyState1.modify(_ + 100) &&!
        MyState2.modify(_ + 1000) &&!
        MyState1.modify(_ + 10000) &&!
        prog(n - 1)

    prog(Mulst.LIMIT)
    .runWith((MyState1.handler(0) ***! MyState2.handler(0)).exec)


  override def round3 =
    def prog(n: Int): Unit !! (MyState1 & MyState2 & MyState3) =
      if n <= 0
      then !!.unit
      else
        MyState1.modify(_ + 1) &&!
        MyState2.modify(_ + 10) &&!
        MyState3.modify(_ + 100) &&!
        MyState1.modify(_ + 1000) &&!
        MyState2.modify(_ + 10000) &&!
        prog(n - 1)

    prog(Mulst.LIMIT)
    .runWith((MyState1.handler(0) ***! MyState2.handler(0) ***! MyState3.handler(0)).exec)


  override def round4 =
    def prog(n: Int): Unit !! (MyState1 & MyState2 & MyState3 & MyState4) =
      if n <= 0
      then !!.unit
      else
        MyState1.modify(_ + 1) &&!
        MyState2.modify(_ + 10) &&!
        MyState3.modify(_ + 100) &&!
        MyState4.modify(_ + 1000) &&!
        MyState1.modify(_ + 10000) &&!
        prog(n - 1)

    prog(Mulst.LIMIT)
    .runWith((
      MyState1.handler(0) ***!
      MyState2.handler(0) ***!
      MyState3.handler(0) ***!
      MyState4.handler(0)
    ).exec)


  override def round5 =
    def prog(n: Int): Unit !! (MyState1 & MyState2 & MyState3 & MyState4 & MyState5) =
      if n <= 0
      then !!.unit
      else
        MyState1.modify(_ + 1) &&!
        MyState2.modify(_ + 10) &&!
        MyState3.modify(_ + 100) &&!
        MyState4.modify(_ + 1000) &&!
        MyState5.modify(_ + 10000) &&!
        prog(n - 1)

    prog(Mulst.LIMIT)
    .runWith((
      MyState1.handler(0) ***!
      MyState2.handler(0) ***!
      MyState3.handler(0) ***!
      MyState4.handler(0) ***!
      MyState5.handler(0)
    ).exec)
