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

object Service {
  implicit val ex1 = scala.concurrent.ExecutionContext.global
}

class Service1 {

  import Service._

  val manager = new Manager()

  def doServiceStuff(): String = {

    println("doing service stuff on " + Thread.currentThread().getName)

    val futureResult  = manager.doManagerStuff()

    val tryResult = Try(  Await.result(futureResult, 10.seconds)  )

    //
    futureResult foreach { x => {
        // TODO: print to console may be considered a blocking operation and may slow down execution context
        println("service on success on " + Thread.currentThread().getName)
      }
    }

    tryResult match {
      case Success(e) => "OK"
      case Failure(e) => "ERROR"
    }


  }

}

object Manager {

  // Manager(s) has deal with slow/'blocking'-operations like IO/Net so we use separate execution context for its work

  implicit val ex2 = scala.concurrent.ExecutionContext.fromExecutor(null: Executor)
}

class Manager {

  import Manager._

  def doManagerStuff(): Future[String] = { // 2. no execution context passed
    val f = Future {
      blocking {
        println("did manager stuff on " + Thread.currentThread().getName)
        "did manager stuff"
      }
    }

    f foreach {case x => println("!!!\n" + x) }

    f
  }

}

object Ex extends App {

  val service = new Service1()

  service.doServiceStuff()

}



