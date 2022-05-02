package effect_zoo.aux.zio_.rws.mono
import zio._


trait Reader[R]:
  trait Service:
    def ask: UIO[R]

  final def ask: URIO[Has[Service], R] = ZIO.serviceWith[Service](_.ask)

  
  final case class Live(r: R) extends Service:
    override def ask: UIO[R] = ZIO.succeed(r)

  object Live:
    def layer(r: R): ULayer[Has[Service]] = ZLayer.succeed(Live(r))
