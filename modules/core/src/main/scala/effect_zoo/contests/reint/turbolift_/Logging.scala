package effect_zoo.contests.reint.turbolift_
import turbolift.{!!, Signature, Effect, Handler}
import turbolift.effects.WriterK


trait LoggingSignature extends Signature:
  def logMsg(text: String): Unit !@! ThisEffect

case object Logging extends Effect[LoggingSignature] with LoggingSignature:
  override def logMsg(text: String): Unit !! ThisEffect = perform(_.logMsg(text))

type Logging = Logging.type  


val accumulateLogMessages: Handler.Id[Logging, LogWriter] =
  new Logging.Proxy[LogWriter] with LoggingSignature:
    override def logMsg(text: String): Unit !@! ThisEffect = LogWriter.tell(text)
  .toHandler


case object LogWriter extends WriterK[Vector, String]

type LogWriter = LogWriter.type
