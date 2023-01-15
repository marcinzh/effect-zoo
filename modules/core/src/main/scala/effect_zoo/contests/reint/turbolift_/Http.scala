package effect_zoo.contests.reint.turbolift_
import turbolift.{!!, Signature, Effect, Handler}
import turbolift.effects.Reader


trait HttpSignature extends Signature:
  def get(url: String): String !@! ThisEffect

case object Http extends Effect[HttpSignature] with HttpSignature:
  override def get(url: String): String !! ThisEffect = perform(_.get(url))

type Http = Http.type  


val mockResponses: Handler.Id[Http, ResponseReader] =
  new Http.Proxy[ResponseReader] with HttpSignature:
    override def get(url: String): String !@! ThisEffect = _ => ResponseReader.ask
  .toHandler


case object ResponseReader extends Reader[String]

type ResponseReader = ResponseReader.type
