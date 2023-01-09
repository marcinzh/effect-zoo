package effect_zoo.auxx.zio_.rws.layer
import zio._


trait State[S]:
  def get: UIO[S]
  def put(s: S): UIO[Unit]
  def update(f: S => S): UIO[Unit]

object State:
  def get[S: Tag]: URIO[State[S], S] = ZIO.serviceWithZIO[State[S]](_.get)
  def put[S: Tag](s: S): URIO[State[S], Unit] = ZIO.serviceWithZIO[State[S]](_.put(s))
  def update[S: Tag](f: S => S): URIO[State[S], Unit] = ZIO.serviceWithZIO[State[S]](_.update(f))


case class StateLive[S: Tag](ref: Ref[S]) extends State[S]:
  override def get: UIO[S] = ref.get
  override def put(s: S): UIO[Unit] = ref.set(s)
  override def update(f: S => S): UIO[Unit] = ref.update(f)

object StateLive:
  def layer[S: Tag](s: S): ULayer[State[S]] = ZLayer.fromZIO(Ref.make[S](s).map(StateLive.apply))
