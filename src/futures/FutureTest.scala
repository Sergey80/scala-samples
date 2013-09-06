package futures

import scala.concurrent._
import scala.collection.mutable
import ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

/*
 #future
 related:
 */
object FutureTest extends App {

  def getData(): Iterable[Int] = {

    val data = mutable.MutableList[Int]()

    for (i <- 1 to 10) {
      Thread.sleep(100) // emulating some delay in getting the data
      data += i
    }

    data
  }

  val f = future { getData() }

  // there are two 'onSuccess' - they are subscribes for future success, both of them will be eventually invoked

  // #1
  f.onSuccess {
    case data => for (item <- data) println (item)
  }

  // #2
  f.onSuccess {
    case data => println ("the amount of of item received from data: " + data.size)
  }

  println ("hello!")  // this will be printed first, then #2, and then #1

  // wait until done
  while(!f.isCompleted) {Thread.sleep(1000)}

}
