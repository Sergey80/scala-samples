package mainfunctions

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  * There are essential set of function that one should learn and know as alphabet.
  * Bellow some simple example with explanations how to use them.
  *
  * Here we cover Joins
  *
  * Also, here in the doc: https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.rdd.PairRDDFunctions - it is all about pairs.
  */

class SparkFunctions2Spec extends FlatSpec with Matchers  with BeforeAndAfter  {

  val conf: SparkConf = new SparkConf()
    .setMaster("local[2]") // two threads
    .setAppName("step2")

  var sc: SparkContext = new SparkContext(conf)

  val userSubscriptionList = List(
    (1, ("user1", "subscription1")),
    (2, ("user2", "subscription2")),
    (3, ("user3", "subscription1")),
    (4, ("user4", "subscription3")),
    (5, ("user5", "subscription3"))              // this user does not exist in userCity
  )
  val userSubscriptionRDD = sc.parallelize(userSubscriptionList)

  val userCityList = List(
    (1, ("user1", "Montreal")),
    (2, ("user2", "Ottawa")),
    (3, ("user3", "Montreal")),
    (4, ("user4", "Sherbrook"))
  )

  before {

  }

  after {
    //sc.stop()
  }

  "join" should "show how it works - inner join" in {

    val userCityRDD = sc.parallelize(userCityList)

    val result = userSubscriptionRDD.join(userCityRDD)  // join assumes that both RDD has the same-type keys

    result.collect().length should equal(4) // not 5 (because it is inner join. there is no user5 with id = 5)

  }

  "joinLeft vs Right" should "show how it works - left join" in {

    val userCityRDD = sc.parallelize(userCityList)

    val resultLeft = userSubscriptionRDD/*5 records*/.leftOuterJoin(userCityRDD /* 4 records */)
    val resultRight = userSubscriptionRDD/*5 records*/.rightOuterJoin(userCityRDD/* 4 records */)

    resultLeft.collect().length should equal(5) // user5 is on the left side, with id = 5, that is on the left side
    resultRight.collect().length should equal(4) // there is not user5 on the right side with id = 5, that is on the left side

    // resultRight: (NoteL lef it optional that's we we see Some(..) here
    // (4, (Some((user4, subscription3)), (user4, Sherbrook)
    // (2, (Some((user2, subscription2)), (user2, Ottawa)
    // (1, (Some((user1, subscription1)), (user1, Montreal)
    // (3, (Some((user3, subscription1)), (user3, Montreal)

    //
    val rightOuterJoinReverse = userCityRDD/*4 records*/.rightOuterJoin(userSubscriptionRDD/*5 records*/).collect()
    rightOuterJoinReverse.length should equal(5) // 5, since if we do rightJoin then it is guaranteed to have 5 records from the right hand side

    // (5,(None,(user5,subscription3)))
  }


}