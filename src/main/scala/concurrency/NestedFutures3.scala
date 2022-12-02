package concurrency

import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future, blocking}


object NestedFutures3 extends App {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  val ec2: ExecutionContext = new ExecutionContext {
    val threadPool: ExecutorService = Executors.newFixedThreadPool(1000)
    def execute(runnable: Runnable): Unit = {
      threadPool.submit(runnable)
    }
    def reportFailure(t: Throwable): Unit = {}
  }

  def parent(a:Int)(implicit ec: ExecutionContext) = Future[Int] {

    Thread.sleep(1000)

    println("parent: " + Thread.currentThread.getName)

    (1 to 10) foreach { n =>
      child_blocking(n)(ec2)
    }
    0
  }(ec)

  def child_blocking(a:Int)(ec: ExecutionContext):Future[Int] = {
    Future {
      blocking {
        Thread.sleep(2000)
        println(s" child_blocking:${a}: " + Thread.currentThread.getName)
        1
      }
    }(ec)
  }

  //

  Await.ready( parent(1), Duration.Inf )

  println("done")

  Thread.sleep(10000)

}
