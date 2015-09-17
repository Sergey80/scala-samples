package scalacheck


import org.scalatest.prop.{GeneratorDrivenPropertyChecks, Configuration, Checkers}
import org.scalatest.{FeatureSpec, GivenWhenThen, ShouldMatchers}

// https://github.com/rickynils/scalacheck/wiki/User-Guide
// http://www.scalatest.org/user_guide/writing_scalacheck_style_properties

class ScalaCheckTest extends FeatureSpec with GivenWhenThen with ShouldMatchers

                                                // ScalaCheck support
                                                /*with Checkers*/ with GeneratorDrivenPropertyChecks /*with Configuration*/ {

  info("test..")

  //implicit override val generatorDrivenConfig = PropertyCheckConfig(minSize = 10, maxSize = 20)

  feature("feature1") {
    scenario("scenario1") {

      //import org.scalacheck.Prop.forAll   // pure scalacheck

      Given("given1..")

      When("when..")
      // val propReverseList = forAll { l: List[String] => l.reverse.reverse == l }

      When("when..")
      //val propSqrt = forAll { (n: Int) => scala.math.sqrt(n*n) == n }

      Then("then...")
      //propReverseList.check
      //propSqrt.check

      //
      //check( (l: List[String]) => l.reverse.reverse == l )

    }


    scenario("scenario2 - sqrt") {
      // with GeneratorDrivenPropertyChecks

      //val generatorDrivenConfig = PropertyCheckConfig(minSize = 0, maxSize = 1)

      val conf = PropertyCheckConfig // ..

//      forAll { (n: Int) =>
//        scala.math.sqrt(n * n) should be(n)
//      }

    }

    scenario("scenario with myMagicFunction") {

      Given("given a myMagicFunction")

      def myMagicFunction(n: Int, m: Int) = n + m

      Then("all should be ok")

//      forAll { (m: Int, n: Int) =>
//        val res = myMagicFunction(n, m)
//        res should (be >= m and be >= n and be < m + n)
//      }

    }

    scenario("...") {



    }


  }

  }
