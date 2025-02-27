package concurrency.apptmpl.controllers

import concurrency.apptmpl.managers.{Manager1}
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}


object Controller1 {
  import scala.concurrent.ExecutionContext
  implicit val ex1: ExecutionContext = Controllers.ex1

  val manager1 = Manager1

  def doControllerStuff2(): String = {
    implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

    val futureResult  = manager1.doManagerStuff2()

    val tryResult = Try(Await.result(futureResult, 10.seconds))

    tryResult match {
      case Success(r) => "OK:" + r
      case Failure(e) => "ERROR: " + e
    }
  }

  def doControllerStuff1(): String = {
    implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

    println("doing controller stuff on " + Thread.currentThread().getName)

    val futureResult  = manager1.doManagerStuff1()

    futureResult foreach { x =>
      // TODO: print to console may be considered a blocking operation and may slow down execution context
      println("controller on success on " + Thread.currentThread().getName)
    }

    val tryResult = Try(Await.result(futureResult, 10.seconds))

    tryResult match {
      case Success(e) => "OK:" + e
      case Failure(e) => "ERROR"
    }
  }
}