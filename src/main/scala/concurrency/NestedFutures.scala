package concurrency

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Await, Future, blocking}

object NestedFutures extends App {

  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global
  val cores = Runtime.getRuntime.availableProcessors

  val works: Seq[Future[Seq[Future[String]]]] =

    (1 to cores * 2) map { x =>

      Future {
        blocking {

          val threadName = Thread.currentThread.getName

          println("blocking: " + threadName)
          Thread.sleep(1000)

          // being in 'blocking' we start more nested futures
          // will those future make use of parent 'blocking' and make use of new threads,
          // will they create new threads?
          // Will amount of this new thread be more than amount of cores?
          val fStuffs: Seq[Future[String]] = (1 to 10) map {stuffId =>
            getStuff(parentName = threadName)
          }
          fStuffs
        }
      }
  }


  val result1: Seq[Seq[Future[String]]] = Await.result(Future.sequence(works), Duration.Inf)

  val result2: Seq[String] = Await.result(Future.sequence(result1.flatten), Duration.Inf)

  println("result:")

  result2 foreach println


  def getStuff(parentName:String)(implicit ex:ExecutionContext):Future[String] = {
    Future {
      Thread.sleep(1000)

      val threadName = Thread.currentThread.getName

      println(threadName + " < ---- " + parentName)

      Thread.currentThread.getName
    }
  }

}
