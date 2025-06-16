package effect_zoo.auxx
import kyo.Tag


object OpaqueInts:
  type Int1 = Int
  opaque type Int2 = Int
  opaque type Int3 = Int
  opaque type Int4 = Int
  opaque type Int5 = Int

  extension (thiz: Int)
    inline def toInt1: Int1 = thiz
    inline def toInt2: Int2 = thiz
    inline def toInt3: Int3 = thiz
    inline def toInt4: Int4 = thiz
    inline def toInt5: Int5 = thiz

  object Int1:
    extension (thiz: Int1)
      inline def unwrap: Int = thiz

  object Int2:
    extension (thiz: Int2)
      inline def unwrap: Int = thiz
      inline def +(n: Int): Int2 = thiz.unwrap + n
    // Faking it for "Please provide an implicit kyo.Tag" error
    given Tag[Int2] = Tag[Int2.type].asInstanceOf[Tag[Int2]]

  object Int3:
    extension (thiz: Int3)
      inline def unwrap: Int = thiz
      inline def +(n: Int): Int3 = thiz.unwrap + n
    given Tag[Int3] = Tag[Int3.type].asInstanceOf[Tag[Int3]]

  object Int4:
    extension (thiz: Int4)
      inline def unwrap: Int = thiz
      inline def +(n: Int): Int4 = thiz.unwrap + n
    given Tag[Int4] = Tag[Int4.type].asInstanceOf[Tag[Int4]]

  object Int5:
    extension (thiz: Int5)
      inline def unwrap: Int = thiz
      inline def +(n: Int): Int5 = thiz.unwrap + n
    given Tag[Int5] = Tag[Int5.type].asInstanceOf[Tag[Int5]]
