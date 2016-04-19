package banch

import rx._

// https://vimeo.com/98477272

object ScalaRxSample extends App {

  val a = Var(1)
  val b = Var(2)
  val c = Rx{ a() + b() }
  println(c.now) // 3

  a() = 4

  println(c.now) // 6
}
