// #async #log #async-log #akka #ThreadFactory

package concurrency.logging
import akka.actor.{ActorSystem}
import akka.event.Logging
import concurrency.logging.threadfactory.CustomThreadFactory

import java.util.concurrent.ForkJoinPool
import scala.concurrent.{ExecutionContext, Future, blocking}

// This example shows how to make async logging possible without too much of overhead

object Services { // stores some settings

  private[Services] val cores = Runtime.getRuntime.availableProcessors

  // made use of our own CustomThreadFactory to be able put nicer output for the pools in use

  private[logging] val schedulingPool = new ForkJoinPool(cores, CustomThreadFactory("scheduling"), CustomThreadFactory.uncaughtExceptionHandler, true) // TODO: fix this ...
  private[logging] val blockingWorkPool = new ForkJoinPool(cores, CustomThreadFactory("blockingWork"), CustomThreadFactory.uncaughtExceptionHandler, true)


  val schedulingEx = ExecutionContext.fromExecutor (schedulingPool)
  val bockingWorkEx = ExecutionContext.fromExecutor (blockingWorkPool)
}



object LogSample extends App {

  import Services._
  val system = ActorSystem("system")

  val log = Logging(system.eventStream, "app.logger")

  Future {
    log.info("1")
  }(schedulingEx)

  Future {
    log.info("2")
  }(bockingWorkEx)

  //make sure it still able to create new threads (with 'blocking')
  (1 to 100000) foreach { x =>  // and make sure log-settings (logback.xml) will create new backup/rollover files
    Future {
      blocking { // !
        Thread.sleep(1000)
        log.info(s"2.${x}")
      }
    }(bockingWorkEx)
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
