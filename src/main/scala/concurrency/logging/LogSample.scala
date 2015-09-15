// #async #log #async-log #akka #ThreadFactory

package concurrency.logging

import akka.actor.{ActorSystem}
import akka.event.Logging
import concurrency.logging.threadfactory.CustomThreadFactory

import scala.concurrent.forkjoin.{ForkJoinPool}
import scala.concurrent.{ExecutionContext, Future, blocking}

// This example shows how to make async logging possible without too much of overhead

object Services { // stores some settings

  private[Services] val cores = Runtime.getRuntime().availableProcessors()

  // made use of our own CustomThreadFactory to be able put nicer output for the pools in use

  val ex1 = ExecutionContext.fromExecutor (
    new ForkJoinPool(cores, CustomThreadFactory("my"), CustomThreadFactory.uncaughtExceptionHandler, true)
  )
  val ex2 = ExecutionContext.fromExecutor (
    new ForkJoinPool(cores, CustomThreadFactory("oh-my-my"), CustomThreadFactory.uncaughtExceptionHandler, true)
  )
}


object LogSample extends App {

  import Services._

  val system = ActorSystem("system")

  val log = Logging(system.eventStream, "my.nice.string")

  Future {
    log.info("1")
  }(ex1)

  Future {
    log.info("2")
  }(ex2)

  //make sure it still able to create new threads (with 'blocking')
  (1 to Runtime.getRuntime().availableProcessors() * 2) foreach { x =>
    Future {
      blocking {
        Thread.sleep(1000)
        log.info(s"2.${x}")
      }
    }(ex2)
  }

  Thread.sleep(1000)

  // output, like this:

  /*
    [INFO] [09/15/2015 11:40:07.038] [my-ForkJoinPool-2-worker-7] [my.nice.string] 1
    [INFO] [09/15/2015 11:40:07.038] [oh-my-my-ForkJoinPool-3-worker-7] [my.nice.string] 2
    [INFO] [09/15/2015 11:40:07.039] [oh-my-my-ForkJoinPool-3-worker-5] [my.nice.string] 2.1
    ...
  */

  // see also logback.xml and application.conf in root

}
