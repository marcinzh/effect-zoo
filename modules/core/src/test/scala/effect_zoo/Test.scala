package effect_zoo
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers._
import effect_zoo.registry.Registry


class Test extends AnyFunSpec:
  Registry.all.foreach { reg =>
    val contest = reg.contest
    describe(s"Contest: ${contest.name}") {
      reg.entries.foreach { entry =>
        describe(s"Contender: ${entry.contender.name}") {
          entry.rounds.zipWithIndex.foreach { case (round, index) =>
            describe(s"Round: #${index + 1}") {
              it("should return expected value") {
                round.run() shouldEqual round.expected
              }
            }
          }
        }
      }
    }
  }
