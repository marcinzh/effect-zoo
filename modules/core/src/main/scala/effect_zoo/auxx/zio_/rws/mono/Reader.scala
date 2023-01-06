package effect_zoo.auxx.zio_.rws.mono
import zio._


trait Reader[R]:
  trait Service:
    def ask: UIO[R]

  final def ask: URIO[Service, R] = ZIO.serviceWithZIO[Service](_.ask)

  
  final case class Live(r: R) extends Service:
    override def ask: UIO[R] = ZIO.succeed(r)

  object Live:
    def layer(r: R): ULayer[Service] = ZLayer.succeed(Live(r))
