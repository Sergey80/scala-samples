package futures

import scala.concurrent._
import scala.collection.mutable
import ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object FutureTest extends App {

  def getData(): Iterable[Int] = {

    val data = mutable.MutableList[Int]()

    for (i <- 1 to 10) {
      Thread.sleep(100) // some delay in getting the data
      data += i
    }

    data
  }

  val f = future { getData() }

  f.onSuccess {
    case data => for (item <- data) println (item)
  }

  f.onSuccess {
    case data => println ("the amount of of item received from data: " + data.size)
  }

  println ("hello!")

  // wait until done
  while(!f.isCompleted) {Thread.sleep(1000)}

}
