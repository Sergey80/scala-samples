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

}
