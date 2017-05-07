package tests

/**
  * #aggregate #scalatest #scalamock #mockFunction
  *
  * Explains in test manner what is `aggregate` function is about.
  * Also demonstrate the test-technique like `mockFunction` with MockFactory - scalamock integration
  */

import org.scalatest.{FlatSpec, Matchers}
import org.scalamock.scalatest.MockFactory

// https://duckduckgo.com/?q=scala+aggregate+function&t=canonical&ia=qa&iax=1

class AggregateSpec extends FlatSpec with Matchers with MockFactory {

  // how many characters are in it
  val data = Seq("This", "is", "something", "I", "would", "like", "to", "know")

  "combop function" should "NOT be called for non-par collection" in {

    val mockCombop = mockFunction[Int, Int, Int]

    mockCombop.expects(*, *).never // should not ve called

    val result: Int = data.aggregate(0)(
      seqop = (acc, next) => acc + next.length,
      combop = mockCombop
    )

  }

  "combop function" should "BE called for par collection" in {

    val mockCombop = mockFunction[Int, Int, Int]
    mockCombop.expects(*, *).atLeastOnce()        // should be called

    val parData = data.par

    val result: Int = parData.aggregate(0)(
      seqop = (acc, next) => acc + next.length,
      combop = mockCombop
    )

    result should === (0)                         // that's fine, because 'mockCombop' is mocked

  }

  "aggregate" should "calculate with combop for non-par collection" in {

    val myCombop: (Int, Int) => Int = (a, b) => {
      a + b
    }

    val result: Int = data.aggregate(0)(
      seqop = (acc, next) => acc + next.length,
      combop = myCombop
    )

    result === 31

  }

  "aggregate" should "calculate with combop for par collection" in {

    val myCombop: (Int, Int) => Int = (a, b) => {
      a + b
    }

    val parData = data.par

    val result: Int = parData.aggregate(0)(
      seqop = (acc, next) => acc + next.length,
      combop = myCombop
    )

    result === 31

  }

}