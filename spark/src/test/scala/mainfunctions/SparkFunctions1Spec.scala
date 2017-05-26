package mainfunctions

import mainfunctions.model.Event
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  * There are essential set of function that one should learn and know as alphabet.
  * Bellow some simple example with explanations how to use them.
  *
  * Also, here in the doc: https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.rdd.PairRDDFunctions - it is all about pairs.
  */

class SparkFunctions1Spec extends FlatSpec with Matchers  with BeforeAndAfter  {

  val conf: SparkConf = new SparkConf()
    .setMaster("local[2]") // two threads
    .setAppName("step2")

  var sc: SparkContext = new SparkContext(conf)

  before {

  }

  after {
    //sc.stop()
  }

  "groupByKey" should "show how it works - groups keys" in {

    val rdd = sc.parallelize(
      Seq(
        Event("bob", 2000),
        Event("jonh", 1000),
        Event("bob", 2000)
      )
    )

    val result = rdd.map(event => (event.organizer, event.budget)).
                    groupByKey().
                    collect()

    result.length should equal (2)

    // (jonh,CompactBuffer(1000))
    // (bob,CompactBuffer(2000, 2000))

    // NOTE: Performance-wise:
    /*
    Avoid groupByKey and use reduceByKey or combineByKey instead.
    groupByKey shuffles all the data, which is slow.
      reduceByKey shuffles _only_ the results of sub-aggregations in each partition of the data.
    */
  }

  "reduceByKey" should "show how it works - actually groups by key - applying a function for its values" in {

    val rdd = sc.parallelize(
      Seq(
        Event("bob", 2000),
        Event("jonh", 1000),
        Event("bob", 2000)
      )
    )

    val result: Array[(String, Int)] = rdd.map(event => (event.organizer, event.budget)).
      reduceByKey( (v1: Int, v2: Int) => v1 + v2).  // reduce means - group with applying a function
      collect()

    result.length should equal (2) // jonh: 1000, bob: 4000

    // (jonh,1000)
    // (bob,4000)

    // NOTE: Performance-wise:
    /*
      Avoid groupByKey and use reduceByKey or combineByKey instead.
      groupByKey shuffles all the data, which is slow.
      reduceByKey shuffles _only_ the results of sub-aggregations in _each partition_ of the data.
    */
  }

  "mapValues" should "show how it works - apply map function to the values" in {

    val rdd = sc.parallelize(
      Seq(
        Event("bob", 2000),
        Event("jonh", 1000),
        Event("bob", 2000)
      )
    )

    val result: Array[(String, Int)] = rdd.map(event => (event.organizer, event.budget)).
      mapValues( (v1: Int) => v1 + 10).
      collect()

    result.length should equal (3) // jonh: 10010, bob: 2010, bob: 2010
  }

  "countByKey action" should "show how it works - groups by key - transforming values of count values" in {

    val rdd = sc.parallelize(
      Seq(
        Event("bob", 2000),
        Event("jonh", 1000),
        Event("bob", 2000)
      )
    )

    val eventToOrgBudgetRDD: RDD[(String, Int)] = rdd.map(event => (event.organizer, event.budget))
    eventToOrgBudgetRDD.cache()

    val result: collection.Map[String, Long] = eventToOrgBudgetRDD.countByKey() // actually it groups

    result.size should equal (2) // jonh: 1, bob: 2

    // we can do the same with `mapValues` & `reduceByKey` & collect - that what basically `countByKey` is about

    val result2 = eventToOrgBudgetRDD.
                    mapValues(_ => 1).
                    reduceByKey( (v1, v2) => v1 + v2).
                    collect()

    result2.size should equal (2) // jonh: 1, bob: 2

  }

  "keys" should "show how it works - return RDD with the keys of each tuple" in {

    val rdd = sc.parallelize(
      Seq(
        Event("bob", 2000),
        Event("jonh", 1000),
        Event("bob", 3000)
      )
    )

    val keys = rdd.map(e => (e.organizer, e.budget)).
                keys.collect()

    keys.length should equal (3)
    keys should contain("bob")
    keys should contain("jonh")

  }

// more concrete example using these functions

  "averageBudget" should "compute average budget per event organizer" in {

    val rdd = sc.parallelize(
      Seq(
        Event("bob", 2000),
        Event("jonh", 1000),
        Event("bob", 3000)
      )
    )


    // Calculate a pari (as key's value) containing (budget, #events)
    val intermediate = rdd.map(e => (e.organizer, e.budget)).
                           mapValues(b => (b, 1)). // organizer, (budget, 1)
                           reduceByKey( (v1, v2) => (v1._1 + v2._1, v1._2 + v2._2) )

    val resultRDD = intermediate.mapValues {
      case (budget, nEvents) => ( budget / nEvents, nEvents )
    }

    val result = resultRDD.collect()

    info(result.toString) // jonh (100, 1), bob (2500, 2)

  }

}