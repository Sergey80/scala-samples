package bench_rx

import rx._

// https://vimeo.com/98477272
// https://github.com/lihaoyi/scala.rx (up to date)

object ScalaRxSample extends App {

  val a = Var(1)
  val b = Var(2)
  val c = Rx{ a() + b() }
  println(c.now) // 3

  a() = 4

  println(c.now) // 6

  var count = 0

  //  Observers (Obs s can be created from Rx s or Var s and be used to perform side effects when they change)

  val cObs1: Obs = c.trigger {
    count = c.now + 1
  }
  // same as:
  val cObs2: Obs = c.foreach { x =>
    count = x + 1
  }

  println("count: " + count)

}
