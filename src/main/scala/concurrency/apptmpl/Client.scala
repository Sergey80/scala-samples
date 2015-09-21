package concurrency.apptmpl

import concurrency.apptmpl.controllers.Controller1

import org.scalameter._

object Client extends App {

  val controller1 = Controller1

  //val result1 = controller1.doControllerStuff1()

  val time = measure {

      val result1 = controller1.doControllerStuff2()
      println(result1) // OK:did manager stuff service work done

  }

  println ("took: " + time)
}
