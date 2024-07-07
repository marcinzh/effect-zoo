package effect_zoo.contests.reint.turbolift_
import turbolift.{!!, Signature, Effect, Handler}
import turbolift.effects.Reader
import turbolift.Extensions._


trait HttpSignature extends Signature:
  def get(url: String): String !! ThisEffect

case object Http extends Effect[HttpSignature] with HttpSignature:
  override def get(url: String): String !! ThisEffect = perform(_.get(url))

type Http = Http.type  


val mockResponses: Handler[Identity, Identity, Http, ResponseReader] =
  new Http.impl.Proxy[ResponseReader] with HttpSignature:
    override def get(url: String): String !! ThisEffect = ResponseReader.ask
  .toHandler


case object ResponseReader extends Reader[String]

type ResponseReader = ResponseReader.type
