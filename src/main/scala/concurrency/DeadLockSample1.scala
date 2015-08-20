package concurrency

import java.util.concurrent.Executors
import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Await, Future}

// http://stackoverflow.com/questions/32107833/scala-future-deadlock-fixed-4-thread-pool

object DeadLockSample1 {

    def main(args: Array[String]) {

      implicit val ec = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(4))

      val futures = List( Future{1} )

      val result: Future[List[Int]] = Future.sequence(futures)

      Await.ready(result, Duration.Inf)

    }

}
