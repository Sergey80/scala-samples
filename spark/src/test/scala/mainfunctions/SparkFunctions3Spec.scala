package mainfunctions

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  * There are essential set of function that one should learn and know as alphabet.
  * Bellow some simple example with explanations how to use them.
  *
  * Also, here in the doc: https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.rdd.PairRDDFunctions - it is all about pairs.
  */

class SparkFunctions3Spec extends FlatSpec with Matchers  with BeforeAndAfter  {

  val conf: SparkConf = new SparkConf()
    .setMaster("local[2]") // two threads
    .setAppName("step2")

  var sc: SparkContext = new SparkContext(conf)

  before {

  }

  after {
    //sc.stop()
  }

  "cartesian" should "show how it works" in {

    val rdd: RDD[Int] = sc.parallelize(1 to 5)
    val cartesian = rdd.cartesian(rdd)
    val combinations = cartesian.filter { case (a,b) => a < b }
    val combinationsResult: Array[(Int, Int)] = combinations.collect()

    val cartesianResult = cartesian.collect()
    cartesianResult.length should equal(25)
    combinations.collect().length should equal(10)

    println("=== cartesianResult == ")
    cartesianResult.foreach(println)

    println("=== combinationsResult (filtered) == ")
    combinationsResult.foreach(println)

    /*
    === cartesianResult ==
      (1,1)
      (1,2)
      (2,1)
      ...
      (5,5)
     */
  }
  // we may use 'cartesian' also for query-optimization purposes,
  // to rewrite the `join-filter` type of queries that are way slower
  // comparing to `cartesian-filer` equivalent one (sometimes 100xn times slower!)

  // But actually, that's ok to to go fist with join-filter solution because it is more natural / readable.
  // With some extra structural information for spark we may let spark do this optimization for us (see Spark SQL)

  // TODO: show `cartesian-filer` vs `join-filter`


}