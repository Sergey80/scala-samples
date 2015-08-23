// #thread-pool #thread-reuse #future #blocking

package concurrency


import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, blocking}

object ThreadReuseInConnectionPool extends App {

  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global
  val cores = Runtime.getRuntime.availableProcessors

  // 1 - with n-cores-size thread-pool (no 'blocking is used')
  {
    val allFutures = (1 to cores * 2) map { _ =>
      Future[String] {
        Thread.sleep(500)
        Thread.currentThread().getName
      }
    }
    val threadNames = Await.result(Future.sequence(allFutures), Duration.Inf)
    println(s"1. Unique threads: ${threadNames.toSet.size}") // = cores
  }

  // 2 - with "blocking"
  {
    val allFutures = (1 to cores * 2) map { _ =>
      Future[String] {
        blocking {
          Thread.sleep(500)
          Thread.currentThread().getName
        }
      }
    }
    val threadNames = Await.result(Future.sequence(allFutures), Duration.Inf)
    println(s"2. Unique threads: ${threadNames.toSet.size}") // > cores
  }

  // 3 - after 2-nd test, does ExecutionContext will  use all those new threads-space 
  // created by 'blocking' ?
  {
    val allFutures = (1 to cores * 2) map { _ =>
      Future[String] {
        Thread.sleep(500)
        Thread.currentThread().getName
      }
    }
    val threadNames = Await.result(Future.sequence(allFutures), Duration.Inf)
    println(s"3. Unique threads: ${threadNames.toSet.size}") // = cores
    
    // The answer is "no, it will not use"
  }


}
