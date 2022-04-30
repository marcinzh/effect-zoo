package effect_zoo.contests.reint.turbolift_
import effect_zoo.contests.{Reint, Contender}
import turbolift.!!


object Main extends Reint.Entry(Contender.Turbolift):
  def prog(n: Int): (Vector[String], Vector[String]) =
    !!.replicate(n)(Query.listFruits)
    .map(_.flatten)
    .handleWith(toLoggedHttp)
    .handleWith(accumulateLogMessages)
    .handleWith(mockResponses)
    .handleWith(LogWriter.handler)
    .handleWith(ResponseReader.handler(Reint.Shared.RESPONSE))
    .run


  override def round1 = Reint.Shared.rep1(prog)
  override def round2 = Reint.Shared.rep2(prog)
  override def round3 = Reint.Shared.rep3(prog)
