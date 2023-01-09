package effect_zoo.auxx.zio_.rws.mono
import cats.Monoid
import cats.syntax.semigroup._
import zio._


abstract class Writer[W: Monoid]:
  trait Service:
    def tell(w: W): UIO[Unit]
    def listen[R, E, A](body: ZIO[R, E, A]): ZIO[R, E, (A, W)]

  def tell(w: W): URIO[Service, Unit] = ZIO.serviceWithZIO[Service](_.tell(w))
  def listen[R <: Service, E, A](body: ZIO[R, E, A]): ZIO[R, E, (A, W)] =
    ZIO.serviceWithZIO[Service](ZIO.succeed(_)).flatMap(_.listen(body))

      
  final case class Live(ref: Ref[W]) extends Service:
    override def tell(w: W): UIO[Unit] = ref.update(_ |+| w)
    override def listen[R, E, A](body: ZIO[R, E, A]): ZIO[R, E, (A, W)] =
      for
        w0 <- ref.get
        _ <- ref.set(Monoid[W].empty)
        a <- body
        w1 <- ref.getAndUpdate(w0 |+| _)
      yield (a, w1)

  object Live:
    val layer: ULayer[Service] = ZLayer.fromZIO(Ref.make(Monoid[W].empty).map(Live(_)))
