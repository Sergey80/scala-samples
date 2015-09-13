package concurrency.apptmpl.services

import scala.concurrent.{Future, blocking}

class Service {

  implicit val blockingExContext = Services.blockingExContext

  def doServiceStuff(name:String): Future[String] = {
    Future {
      blocking {

        println ( s"start service ${name} on " + Thread.currentThread.getName)

        Thread.sleep(5000)

        "stuff_done"
      }
    }
  }


}
