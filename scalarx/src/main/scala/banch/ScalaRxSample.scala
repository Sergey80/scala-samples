package banch

import rx._

// https://vimeo.com/98477272

object ScalaRxSample extends App {

  val a = Var(1)
  val b = Var(2)

  val cRx = Rx { a() + b() }

  println("c=" + cRx())  // 3

  a() = 10

  println("c=" + cRx()) // 12

  val o = Obs(cRx) {
    println( "obs(c) = " + cRx() )
  }

  a() = 100

  /***
    * c=3
    * c=12
    * obs(c) = 12
    * obs(c) = 102
    */

}
