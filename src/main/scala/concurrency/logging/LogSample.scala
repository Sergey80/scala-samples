package concurrency.logging

import java.util.concurrent.Executor

import akka.actor.{Props, ActorSystem}
import akka.event.Logging

import scala.concurrent.{ExecutionContext, Future}

object LogSample extends App {

  val ex1 = ExecutionContext.global
  val ex2 = ExecutionContext.fromExecutor(null:Executor)

  val system = ActorSystem("system")

  val log = Logging(system.eventStream, "my.nice.string")

  Future {
    log.info("1")
  }(ex1)

  Future {
    log.info("2")
  }(ex2)

  Thread.sleep(1000)

  // output, like this:

  /*
   [INFO] [09/14/2015 21:53:34.897] [ForkJoinPool-2-worker-7] [my.nice.string] 2
   [INFO] [09/14/2015 21:53:34.897] [ForkJoinPool-1-worker-7] [my.nice.string] 1
  */

  // see also logback.xml and application.conf in root

}
