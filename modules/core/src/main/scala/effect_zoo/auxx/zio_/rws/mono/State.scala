package effect_zoo.auxx.zio_.rws.mono
import zio._


trait State[S]:
  trait Service:
    def get: UIO[S]
    def put(s: S): UIO[Unit]
    def update(f: S => S): UIO[Unit]

  def get: URIO[Service, S] = ZIO.serviceWithZIO[Service](_.get)
  def put(s: S): URIO[Service, Unit] = ZIO.serviceWithZIO[Service](_.put(s))
  def update(f: S => S): URIO[Service, Unit] = ZIO.serviceWithZIO[Service](_.update(f))
      

  final case class Live(ref: Ref[S]) extends Service:
    override def get: UIO[S] = ref.get
    override def put(s: S): UIO[Unit] = ref.set(s)
    override def update(f: S => S): UIO[Unit] = ref.update(f)

  object Live:
    def layer(s: S): ULayer[Service] = ZLayer.fromZIO(Ref.make(s).map(Live(_)))
