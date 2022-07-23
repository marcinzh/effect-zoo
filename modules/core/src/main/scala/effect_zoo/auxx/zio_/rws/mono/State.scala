package effect_zoo.auxx.zio_.rws.mono
import zio._


trait State[S]:
  trait Service:
    def get: UIO[S]
    def put(s: S): UIO[Unit]
    def update(f: S => S): UIO[Unit]

  def get: URIO[Has[Service], S] = ZIO.serviceWith[Service](_.get)
  def put(s: S): URIO[Has[Service], Unit] = ZIO.serviceWith[Service](_.put(s))
  def update(f: S => S): URIO[Has[Service], Unit] = ZIO.serviceWith[Service](_.update(f))
      

  final case class Live(ref: Ref[S]) extends Service:
    override def get: UIO[S] = ref.get
    override def put(s: S): UIO[Unit] = ref.set(s)
    override def update(f: S => S): UIO[Unit] = ref.update(f)

  object Live:
    def layer(s: S): ULayer[Has[Service]] = ZLayer.fromEffect(Ref.make(s).map(Live(_)))
