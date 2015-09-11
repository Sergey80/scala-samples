/*
 * An example of possible app that works with Future and several ex-contexts.
 * The purpose of the app is to get the request
 * and send it back to "business"/"manager" part
*/

package concurrency.apptmpl

import java.util.concurrent.Executor
import scala.concurrent.{Await, Future, blocking}
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

/*
 * "DO" and "DON'T"
 *
 * 1. DON'T inherit implicit execution context but import it directly to each class from Object scope.
 * So we leave more freedom about it and less ambiguity & awkwardness in syntax.
 *
 * 2. DON'T pass execution context from one function to another if we plan to use
 * different execution context in that another function
 *
 *
*/

object Controller {
  val ex1 = scala.concurrent.ExecutionContext.global

  val ex2 = scala.concurrent.ExecutionContext.fromExecutor(null: Executor)
}

class Controller1 {

  //import Service._
  implicit val ex1 = Controller.ex1


  val manager = new Manager()

  def doControllerStuff2(): Unit = {

    implicit val ex1 = Controller.ex2

    val ff = Future {
      println( "ff" + Thread.currentThread.getName )
      1
    }

    ff.foreach{case x =>
      print("ff done")
    }

  }

  def doControllerStuff(): String = {

    println("doing controller stuff on " + Thread.currentThread().getName)

    val futureResult  = manager.doManagerStuff()

    //
    futureResult foreach { x => {
      // TODO: print to console may be considered a blocking operation and may slow down execution context
      println("controller on success on " + Thread.currentThread().getName)
    }
    }


    val tryResult = Try(  Await.result(futureResult, 10.seconds)  )


    tryResult match {
      case Success(e) => "OK:" + e
      case Failure(e) => "ERROR"
    }


  }

}

object Manager {

  // Manager(s) has deal with slow/'blocking'-operations like IO/Net so we use separate execution context for its work

  implicit val ex2 = scala.concurrent.ExecutionContext.fromExecutor(null: Executor)
}

class Manager {

  implicit val ex2 = Manager.ex2

  val service = new Service()

  def doManagerStuff(): Future[String] = { // 2. no execution context passed

        println("did manager stuff on " + Thread.currentThread().getName)

        val serviceResult = service.doServiceStuff()

        serviceResult.map{result =>
          "did manager stuff " + result
        }(Service.ex3) // !
  }

}

object Service {
  implicit val ex3 = scala.concurrent.ExecutionContext.fromExecutor(null: Executor)
}

class Service {

  implicit val ex3 = Service.ex3

  def doServiceStuff(): Future[String] = {
    Future {
      blocking {
        println("doing service stuff")
        "service work done"
      }
    }
  }

}

object Ex extends App {

  val service = new Controller1()

  val result = service.doControllerStuff()
  service.doControllerStuff2()

  println(result) // OK:did manager stuff service work done
}



