package concurrency

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Await, Future, blocking}

/**
 * This example demonstrate a problem with nested Futures and one Global(Implicits.global)
 * execution context.
 *
 * see also: NestedFutures2.scala and SevaralExContexts.scala samples.
*/

object NestedFutures extends App {

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  val cores = Runtime.getRuntime.availableProcessors

  val works: Seq[Future[Seq[Future[String]]]] =

    (1 to cores * 2) map { x =>

      Future {
        blocking { // will create new thread/place if needed

          val parentName = Thread.currentThread.getName

          println("parent: " + parentName + " started an action")

          val playFutureOutcomes: Seq[Future[String]] = (1 to 10) map {stuffId =>
            childPlay(parentName = parentName)
          }


    Thread.sleep(1000)  // that sleep here (represents an addition work to do for the parent),
                              // (after those nested/child futures were sent to execute)
                              // brings us to HTE point of truth

          println(s"[${timeStamp()}] parent: " + parentName + " has finished the action")


          // If not the childs, then all that parents thread would have finished themself in 1 second.

         // (and we may expect parents to finish themself in 1 one second regardless the child existence)

         // But because each new child JOIN the existing pool and is looking for new thread (in the pool) to execute itslef,

         // What if we introduce new separate execution context for children?
         // SEE: NestedFutures2.scala

          playFutureOutcomes
        }
      }
  }


  val result1: Seq[Seq[Future[String]]] = Await.result(Future.sequence(works), Duration.Inf)

  println("parents are done their work")

  val result2: Seq[String] = Await.result(Future.sequence(result1.flatten), Duration.Inf)

  println("result:")
  result2 foreach println

  def childPlay(parentName:String)(implicit ex:ExecutionContext):Future[String] = {
    Future {
      Thread.sleep(2000) // two seconds play session
      val threadName = Thread.currentThread.getName
      // log
      println("child: " + threadName + " of " + parentName + " parent")
      Thread.currentThread.getName
    }(ec)
  }

  def timeStamp(pattern:String = "ss:mm : hh"): String = {
    val fmt = DateTimeFormat.forPattern(pattern)
    fmt.print(DateTime.now)
  }


}
