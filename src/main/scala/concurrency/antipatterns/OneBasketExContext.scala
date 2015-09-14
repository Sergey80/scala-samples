package concurrency.antipatterns

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Await, Future}

// Do not put all on one basket !
// Do not use one execution context for blocking work and for waiting for that work!

// Know that foreach, map, flatMap, for and others 
 // need some execution space to wait/work on/in - the execution context is that 'space'

object OneBasketExContext extends App {

  implicit val ex = ExecutionContext.Implicits.global

  val f1 = Future {1}
  val f2 = Future {2}

  val f3 = Future {
    println ("f3's work is done")
    3
  }

  def getData(id:Int): String = {
      Thread.sleep(3000) // it is not fast to get data
      "a data"
  }

  val coreCount = Runtime.getRuntime().availableProcessors();

  val rr = (1 to coreCount).toList map { x =>

    for {
      v1 <- f1
      v2 <- f2
    } yield getData(id = v1 + v2) // it's blocking. and it will block our only ex context

  }

  // because all cores a busy, there is nobody to server 'foreach'.
  // 'foreach' uses the same exContext as 'f1', 'f2' do
  f3 foreach { v =>
    println("acknowledge f3's work: " + v)   // this will be printed after f1,f2 is done. Even though f3's works was done before
  }

  val data = Await.result(Future.sequence(rr), Duration.Inf)

  println("got: " + data)

}
