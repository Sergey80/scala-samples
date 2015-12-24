package concurrency

import java.util.concurrent.Executors

import org.joda.time.{DateTime}
import org.joda.time.format.{DateTimeFormat}

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Await, Future, blocking}

import org.scalameter._

// In this example we tried to fix 'NestedFutures' by introducing more execution context.
// To let children to operate on their own Ex context.

// But can see that if we have 'global' execution context as parent (which is based on ForkjoinPool),
// then whatever we pass as execution context to the child Future still will use Global context
// and still will be using ONE connection pool.
// And then therefore wll have performance problem if children do not use 'blocking' -
// parents will be stuck in cores-long connection pool

// So: Children will 'inherit' execution context, but not 'blocking'
// and as children connected to the execution context the parents will not able to do
// anything about it but wait for the free space in the pool (when child is done)
// new additional threads will not be creating for the next parent because (I guess)
// Global execution context will not see the reason of/for it.

// Like a snapshot of the moment of time:
// Connection Pool: [ch1, ch2, ch3, p1]

// It things : "Ok there is one parent (p1) in the pool if one more parent
// future will come I create for it new thread it the pool since I see 'blocking' instruction there".

// But because we have more joined children than parents in the list
// the moment when Global Ex context can help parent will never come.

// UPDATE:
// to fix it: add "implicit" to "ex:" param to childPlay or
// pass ex to Future{}(ex) in childPlay function - then all children will play in
// "pool2-thread-1" - "pool2-thread-10"


object ExContexts {
  val main = scala.concurrent.ExecutionContext.Implicits.global
  val ecChildren = scala.concurrent.ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))
}

object NestedFutures2 extends App {

  import scala.concurrent.ExecutionContext.Implicits.global

  val cores = Runtime.getRuntime.availableProcessors

  val time = measure {

    val works = parentWork(ExContexts.main) // main EC (based on fork-join pool ) !

    val result1: Seq[Seq[Future[String]]] = Await.result(Future.sequence(works), Duration.Inf)

    println(s"[${timeStamp()}] parents are done with their work")

    val result2: Seq[String] = Await.result(Future.sequence(result1.flatten), Duration.Inf)
  }

  println(s"running time: $time")

  // ---

  def parentWork(ex:ExecutionContext): Seq[Future[Seq[Future[String]]]] = {

    val works: Seq[Future[Seq[Future[String]]]] =

      (1 to cores * 2) map { x =>

        Future {
          blocking { // will create new thread/place if needed

            val parentName = Thread.currentThread.getName

           // println(s"[${timeStamp()}] parent: " + parentName + " started an action")

            val playFutureOutcomes: Seq[Future[String]] = (1 to 10) map {stuffId =>
              childPlay(parentName = parentName)(ExContexts.ecChildren)         // separate context !
            }

            Thread.sleep(1000)

            println(s"[${timeStamp()}] parent: " + parentName + " has finished the action")

            playFutureOutcomes
          }
        }
      }
    works
  }

  def childPlay(parentName:String)(/*implicit*/ ex:ExecutionContext):Future[String] = {
    Future {
        Thread.sleep(20000) // two seconds play session
        val threadName = Thread.currentThread.getName
        // log
        println(s"[${timeStamp()}] child: " + threadName + " of " + parentName + " parent")
        Thread.currentThread.getName
    }(ex) // THE FIX: or uncomment "implicit" in an argument  <--------------------------------------
  }

  def timeStamp(pattern:String = "ss:mm : hh"): String = {
    val fmt = DateTimeFormat.forPattern(pattern)
    fmt.print(DateTime.now)
  }


}
