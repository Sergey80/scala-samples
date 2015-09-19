package concurrency.antipatterns


import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContextExecutor, ExecutionContext, Future}


import ExecutionContext.Implicits.global  // ! my IDE marks  it as not used, but I should not believe in it

object ExecutionContextTrap {
  def main(args: Array[String]) {

    val pool = Executors.newFixedThreadPool(4)

    implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(pool)


    println("global:" + ExecutionContext.Implicits.global)    // ExecutionContextImpl
    println("ec: " + ec)                                      // ExecutionContextImpl

    println("--------------")

    // NOTE that in implicit scope we have this:
    // implicit val global : scala.concurrent.ExecutionContextExecutor          -> ExecutionContextImpl (1)
    // implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(pool)  -> ExecutionContextImpl (2)

    Future {
        println(Thread.currentThread.getName)
        Thread.sleep(1000)
    } // expects instance of ExecutionContext  // ExecutionContextImpl from 'ec' !

    val who = implicitly[ExecutionContext]    // ExecutionContextImpl from 'global' !
    println("who:" + who)

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

// Q: Why Future {}() does not follow 'implicitly'-logic?