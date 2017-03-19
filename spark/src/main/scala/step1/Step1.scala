package step1

// #first #spark #config #file #rdd #collect #transformation #action
import java.io.File

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

// How to start the Apache Spark

case class DataLine(line: String)

object Step1 extends App {

  val conf: SparkConf = new SparkConf()
    .setMaster("local[2]") // two threads - which represents “minimal” parallelism, which can help detect bugs that only exist when we run in a distributed context.
    .setAppName("fist-thing-first")

  val sc: SparkContext = new SparkContext(conf)

  // --

  val file = new File(this.getClass.getClassLoader.getResource("step1/sample-data.txt").toURI).getPath

  val dataLineRdd: RDD[DataLine] = sc.textFile(file).map { (line: String) =>
    //println(line)// just to print what we are getting
    DataLine(line)
  }

  // #transfomration(lazy)

  val wordsRdd: RDD[String] = dataLineRdd.flatMap { dataLine =>  // in case of dataLineRdd.map - we would have had: RDD[Array[String]], so we flatting it out up to the RDD[String]
    dataLine.line.split(" ")
  }

  // rdd is a lazy thing, we have to apply an #action to start evaluation

  val count = wordsRdd.count() // count-Action

  println("Word Count:" + count) // 6

  sc.stop()

}

// Check the log when you start it.