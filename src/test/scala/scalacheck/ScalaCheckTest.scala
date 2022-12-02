package scalacheck


import org.scalatest.GivenWhenThen
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
//import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks


// https://github.com/rickynils/scalacheck/wiki/User-Guide
// http://www.scalatest.org/user_guide/writing_scalacheck_style_properties

class ScalaCheckTest extends AnyFeatureSpec with GivenWhenThen

                                                // ScalaCheck support
                                                /*with Checkers*/ with ScalaCheckDrivenPropertyChecks /*with Configuration*/ {

  info("test..")

  //implicit override val generatorDrivenConfig = PropertyCheckConfig(minSize = 10, maxSize = 20)

  Feature("feature1") {
    Scenario("scenario1") {

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


    Scenario("scenario2 - sqrt") {
      // with GeneratorDrivenPropertyChecks

      //val generatorDrivenConfig = PropertyCheckConfig(minSize = 0, maxSize = 1)

      //val conf = PropertyCheckConfig // ..

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


/*

package test.formatter

import java.text.SimpleDateFormat
import java.util.Date

import org.scalatest.prop.{GeneratorDrivenPropertyChecks, Configuration, Checkers}
import org.scalatest.{FeatureSpec, GivenWhenThen, ShouldMatchers}

import scala.concurrent.{ExecutionContext, Await, Future, blocking}
import scala.concurrent.duration._

import org.scalameter._

class FormatterTest extends FeatureSpec with GivenWhenThen with ShouldMatchers with GeneratorDrivenPropertyChecks {

  val DATE_FORMATTER1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  def DATE_FORMATTER2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  feature("feature1") {

        scenario("singleton formatter") {

        Given("Time")

            //val date = new Date
            val clientCount = 2000000
            //val expectedTextDate = DATE_FORMATTER1.format(date)

            //println("expectedDate: " + expectedTextDate)

        When("Singleton - Many clients format at the same time")

            implicit val _ex = ExecutionContext.global

            val futureDates = (0 to clientCount).toList map {n =>
              Future {
                blocking {

                  Thread.sleep(1000)

                  val date = new Date

                  val text = DATE_FORMATTER1.format(date)
//                DATE_FORMATTER1.parse(text)

                  text
                }
              }
            }

            val f =  Future.sequence(futureDates)

            val time = measure {
              Await.ready( f, Duration.Inf)
            }

            println("execution time: " + time + " sec")

         Then("All dates should be the same")

          val dates =  Await.result( f, Duration.Inf)

          println("size: " + dates.size)

            dates.foreach {date =>

              //println(date)

              date.contains("2015-09-25") should be(true)

            }


        }
  }
}

*/



/*
public class Test {

    public static void main(String[] args) throws Exception {

        final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Callable<Date> task = new Callable<Date>(){
            public Date call() throws Exception {
                return format.parse("2015-10-22");
            }
        };

        //pool with 5 threads
        ExecutorService exec = Executors.newFixedThreadPool(5);
        List<Future<Date>> results = new ArrayList<Future<Date>>();

        //perform 2 date conversions
        for(int i = 0 ; i < 2 ; i++){
//            Thread.sleep(1);
            results.add(exec.submit(task));
        }
        exec.shutdown();

        //look at the results
        for(Future<Date> result : results){
            System.out.println(result.get());
        }
    }
}
*/
