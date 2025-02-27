package futures

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.collection.mutable

/*
 #future
 related:
 */
object FutureTest extends App {
  def getData(): Iterable[Int] = {
    val data: mutable.ArrayDeque[Int] = mutable.ArrayDeque[Int]()

    for (i <- 1 to 10) {
      Thread.sleep(100) // emulating some delay in getting the data
      data += i
    }

    data
  }

  val f = Future {
    getData()
  }

  // there are two 'onComplete' - they are subscribes for future success, both of them will be eventually invoked

  // #1
  f.onComplete {
    case scala.util.Success(data) => for (item <- data) println(item)
    case scala.util.Failure(ex) => println(s"An error occurred: ${ex.getMessage}")
  }

  // #2
  f.onComplete {
    case scala.util.Success(data) => println("the amount of items received from data: " + data.size)
    case scala.util.Failure(ex) => println(s"An error occurred: ${ex.getMessage}")
  }

  println("hello!") // this will be printed first, then #2, and then #1

  // wait until done
  while (!f.isCompleted) {
    Thread.sleep(1000)
  }
}