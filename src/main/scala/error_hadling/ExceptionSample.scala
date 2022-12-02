package ExceptionSample

import scala.util.Try
import scala.io.BufferedSource
import java.io.IOException

// #exceptions. #Try

object ExceptionSample extends App {

 // # 1 syntax: "catch { case ..."
 {

    def loadFile(filename: String, encoding: String = "utf-8"): Option[String] = {
      try {
        val source = scala.io.Source.fromFile(filename, encoding)
        val contents = source.mkString
        source.close()
        Some(contents)
      } catch {  // catch expect PartialFunction
        case x: IOException =>  return None
        case x: Throwable => errorHandler(x) // if any other exception
      }
    }

    def errorHandler(value: Any) = None

    val result = loadFile("")

    println( result )  // None

}

 // #2 scala.util.Tty   -- check this ouy it is much easier, seems
{


  val result = Try {scala.io.Source.fromFile("some-file.txt", "UTF-8")}.toOption

  result match {                   // so we are handling error on Caller side. Let's caller decide how to go with error
    case None => println (result)
    case s:Some[BufferedSource] => println (s"bufferSource $s is here")
  }
}


}
