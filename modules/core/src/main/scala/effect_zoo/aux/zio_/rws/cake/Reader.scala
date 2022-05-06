package effect_zoo.aux.zio_.rws.cake
import zio._


trait Reader[R]:
  def reader: Ref[R]

object Reader:
  def ask[R] = ZIO.accessM[Reader[R]](_.reader.get)
