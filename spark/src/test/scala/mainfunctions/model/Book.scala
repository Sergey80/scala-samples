package mainfunctions.model

import org.apache.spark.rdd.RDD
import step1.Step2.readableTextLines

object Book {

  private val readableChars: Set[Char] = (('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')).toSet

  private def isReadableText(text: String): Boolean = {
    text.exists(readableChars.contains)
  }

  def words(rddLine: RDD[String]): RDD[String] = {
    readableTextLines.flatMap(line => line.split(" "))
  }


}
