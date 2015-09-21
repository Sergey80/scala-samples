// #thread #starvation #futures #thread-reuse
package concurrency

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, blocking}
import org.scalameter._

object ThreadStarvation extends App {

  /*
      default/ global Ex Context (thread pool) by default deals with n default active threads,
      were n is amount of cores on particular machine
   */
  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global

  val cores = Runtime.getRuntime.availableProcessors

  // --- 1 ---

  val time1 = measure {

    // creates amount of threads / futures in 2 times more than ExContext may handle at a time

    val works = (1 to cores * 2) map { x =>
      Future {
        Thread.sleep(1000) // each tread takes 1 second to run
        println(s"work $x is done")
      }
    }
    Await.ready(Future.sequence(works), Duration.Inf) // half of all threads will be execution in parallel (another half will be waiting for its turn)
  }

  // as a result the execution time will take 2 seconds

  println("running time1: " + time1) // 2 sec

  // --- 2 ---

  val time2 = measure {

    val works = (1 to cores * 2) map { x =>
        Future {
          blocking {                    // ! here we believe that the execution may take time
            Thread.sleep(1000)                // so we marking this execution with 'blocking'
            println(s"work $x is done")         // to give a hint for Ex Context to create new addition threads if needed
          }
        }
    }
    Await.ready(Future.sequence(works), Duration.Inf)
  }

  // as a result, the execution time will take ~1 sec
    // since all threads will be executing in max "cores * 2" threads -
    // none of them will wait for the free space in the poll to execute

  println("running time2: " + time2) // 1 sec


  // --- 3 ---

  // of course if we have much more works to do),
  // then even if we use 'blocking'

  val time3 = measure {

    val works = (1 to cores * 10000) map { x =>   // 10000 more than cores!
      Future {
        blocking {                   // !
          Thread.sleep(1000)
          println(s"work $x is done")
        }
      }
    }
    Await.ready(Future.sequence(works), Duration.Inf)
  }

  println("running time3: " + time3) // 10.89 sec. Not bad at all

  // for 1000 workers it would take only 2.4 sec !

}

