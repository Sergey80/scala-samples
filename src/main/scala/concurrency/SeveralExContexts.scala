package concurrency

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContextExecutor, ExecutionContext, Future}

// Here we see that indeed if we don't use Implicits.global as parent one then we don't
// have that performance problems that were described samples:
// NestedFuture.scala and NestedFuture2.scala

object SeveralExContexts extends App {

  //val ec1 = scala.concurrent.ExecutionContext.Implicits.global
  val ec1 = scala.concurrent.ExecutionContext.fromExecutor(Executors.newFixedThreadPool(10))
  val ec2 = scala.concurrent.ExecutionContext.fromExecutor(Executors.newFixedThreadPool(40))

//  fun1(ec1)
//  fun1(ec2)

  parentWork(ec1)

  Thread.sleep(2000)

  def parentWork(implicit ec:ExecutionContext): Future[Int] = {

    Future[Int] {

      val name = Thread.currentThread.getName
      println(name)

      (1 to 10) foreach { _ =>
        childPlay(ec2)
      }
      1
    }

  }

  def childPlay(implicit ec:ExecutionContext): Future[Int] = {
    Future[Int] {
      Thread.sleep(1000)
      val name = Thread.currentThread.getName
      println(" " + name)
      0
    }
  }

}

/*
package ex

import java.util.concurrent.Executor

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

// DON'T inherit implicit execution context but import it directly to each class from Object scope. So we leave more freedom about it and less ambiguity & awkwardness in syntax.

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
  implicit lazy val ex2 = scala.concurrent.ExecutionContext.fromExecutor(null: Executor)
}

class Manager {

  import Manager._

  def doManagerStuff(): Future[String] = {
    val f = Future {
      println("did manager stuff on " + Thread.currentThread().getName)
      "did manager stuff"
    }

    f foreach {case x => println("!!!\n" + x) }

    f
  }

}

object Ex extends App {

  val service = new Service1()

  service.doServiceStuff()

}



*/
