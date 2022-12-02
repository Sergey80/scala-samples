package concurrency.antipatterns


import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}

/**
 * @see implicits.ImplicitlyLookupSample to grasp how implicit does lookup
*/


// Don't try to override/overlap one context by another.
// Like the implicit Global Ex Context by new one down the line after.
// You may think your Future{}() would use ex Context you implicitly put after
// but it is not the case - consider to look at how implicit does look up:

// 1. adding a value into implicit scope
import ExecutionContext.Implicits.global  // ! it is in use

object ExecutionContextTrap {

  def main(args: Array[String]): Unit = {

    val pool = Executors.newFixedThreadPool(4)

    // 2. adding value into implicit scope
    implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(pool)

    println("global:" + ExecutionContext.Implicits.global)    // ExecutionContextImpl
    println("ec: " + ec)                                      // ExecutionContextImpl

    println("--------------")

    /* NOTE that in implicit scope we have this:
     implicit val global : scala.concurrent.ExecutionContextExecutor          -> ExecutionContextImpl (1)
     implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(pool)  -> ExecutionContextImpl (2)
    */

    Future {
        println("future1: " + Thread.currentThread.getName) // #1 ForkJoinPool-1-worker-5
        Thread.sleep(1000)
    }(ec) // TRAP!  // ExecutionContextImpl from 'global'         !!!

    Future {
      println("future2: " + Thread.currentThread.getName) // #2 pool-1-thread-1
      Thread.sleep(1000)
    }(ec)  // explicitly passing 'ec'            so now we see that #1 and #2 are different !!!

    //  Like

    // 1. Future to Implicit scope:
    // Give me a value by type ExecutionContext as key

    // 2. Implicit scope:
    // Ok, how many implementations of type ExecutionContext in the key row?
      // "ExecutionContextExecutor" itself and "ExecutionContextExecutor"
    // who of them is less abstract? Who is a child?
      // ExecutionContextExecutor! because ExecutionContextExecutor extends ExecutionContextExecutor
    // then ExecutionContextExecutor is a key - and it refers to - > ExecutionContextImpl
    // here we go - you get a ExecutionContextImpl (1)

    pool.shutdown()

    Thread.sleep(1000)
  }
}
