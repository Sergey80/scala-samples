// #alice #filtering #cache #word-count
package step1

import java.io.File
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

// A meaningful example, analysing Alice In Wonder Land Book

object Step2 extends App {

  val conf: SparkConf = new SparkConf()
    .setMaster("local[2]") // two threads
    .setAppName("step2")

  val sc: SparkContext = new SparkContext(conf)

  // -- operations on RDD: filter, map, count

  val file = new File(this.getClass.getClassLoader.getResource("alice-in_wonder_land.txt").toURI).getPath


  val allTextLines: RDD[String] = sc.textFile(file)

  val readableChars: Set[Char] = (('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')).toSet
  def isReadable(s:String) = s.exists(readableChars.contains)

  val readableTextLines = allTextLines.filter(isReadable)

  // val emptyLines = text.filter(line => !isOrdinary(line)) // empty lines, or lines with no readable content

  val words = readableTextLines.flatMap(line => line.split(" "))

  words.cache()  // a bit of optimization (cache is a synonym of persist with MEMORY_ONLY storage level.)

  val distinctWords = words.distinct() // from the cache

  println("text: " + allTextLines.count())           // 1071
  println("textLines: " + readableTextLines.count()) // 971
  println("words: " + words.count())                 // 27354

  println("distinct words: " + distinctWords.count()) // 5194 - this is how many words we have to know to be able to read this book

  // --

  // TODO:
}