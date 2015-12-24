package concurrency

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContextExecutor, ExecutionContext, Future}

// Here we see that indeed if we don't use Implicits.global as parent one then we don't
// have that performance problems that were described samples:
// NestedFuture.scala and NestedFuture2.scala

// + some ideas about Loggining:http://stackoverflow.com/questions/32491795/logging-with-akka-app-design

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
