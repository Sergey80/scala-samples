package concurrency

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, blocking}

import org.scalameter._

object ThreadStarvation extends App {

  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global

  val time1 = measure {
    
    val works = (1 to 8) map { x =>
      Future {
        Thread.sleep(1000)
        println(s"work $x is done")
      }
    }
    Await.ready(Future.sequence(works), Duration.Inf)
  }

  println("running time1: " + time1.toFloat / 1000F + " sec") // 2 sec

  //

  val time2 = measure {

    val works = (1 to 8) map { x =>
        Future {
          blocking {                    // !
            Thread.sleep(1000)
            println(s"work $x is done")
          }
        }
    }
    Await.ready(Future.sequence(works), Duration.Inf)
  }

  println("running time2: " + time2.toFloat / 1000F + " sec") // 1 sec
}
