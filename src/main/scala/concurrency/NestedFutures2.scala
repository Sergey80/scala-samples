package concurrency

import java.util.concurrent.Executors

import org.joda.time.{DateTime}
import org.joda.time.format.{DateTimeFormat}

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Await, Future, blocking}


object ExContexts {
  val main = scala.concurrent.ExecutionContext.Implicits.global
  val ecChildren = scala.concurrent.ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))
}

object NestedFutures2 extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  val cores = Runtime.getRuntime.availableProcessors

  val works = parentWork(ExContexts.main) // main EC

  val result1: Seq[Seq[Future[String]]] = Await.result(Future.sequence(works), Duration.Inf)
  println("parents are done their work")
  val result2: Seq[String] = Await.result(Future.sequence(result1.flatten), Duration.Inf)

  // ---

  def parentWork(ex:ExecutionContext): Seq[Future[Seq[Future[String]]]] = {

    val works: Seq[Future[Seq[Future[String]]]] =

      (1 to cores * 2) map { x =>

        Future {
          blocking { // will create new thread/place if needed

            val parentName = Thread.currentThread.getName

            println("parent: " + parentName + " started an action")

            val playFutureOutcomes: Seq[Future[String]] = (1 to 10) map {stuffId =>
              childPlay(parentName = parentName)(ExContexts.ecChildren)
            }

            Thread.sleep(1000)

            println(s"[${timeStamp()}] parent: " + parentName + " has finished the action")

            playFutureOutcomes
          }
        }
      }
    works
  }

  def childPlay(parentName:String)(ex:ExecutionContext):Future[String] = {
    Future {
      Thread.sleep(2000) // two seconds play session
      val threadName = Thread.currentThread.getName
      // log
      println("child: " + threadName + " of " + parentName + " parent")
      Thread.currentThread.getName
    }
  }

  def timeStamp(pattern:String = "ss:mm : hh"): String = {
    val fmt = DateTimeFormat.forPattern(pattern)
    fmt.print(DateTime.now)
  }


}
