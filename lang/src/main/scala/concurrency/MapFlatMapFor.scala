package concurrency

import scala.concurrent.{ExecutionContext, Future, blocking}

// #future #map #flatMap #for-comprehension
object MapFlatMapFor extends App {

  implicit val ex = ExecutionContext.Implicits.global   // will be used by map/flatMap/for

  val f1 = Future {1}
  val f2 = Future {2}

  // at this moment these Futures is already started

  // #1. map
  {
    val future3: Future[Future[Int]] = f1.map {x2 =>
      f2.map { x1 =>
        x1 + x2
      }
    }

    future3 foreach { (v: Future[Int]) =>  // not yet value (that why we use FlatMap in next step)
      println("v1: " + v) // Promise   - no really nice output here...
    }

  }

  // #2. flatMap
  {
    val future3: Future[Int] = f1.flatMap { x => // flatMap[S](f: T => Future[S])
      f2.map(y => x + y)                         // map[S](f: T => S)

      // How 'flatMap' works:
      //  - when/as 'f1' is done successfully,
      //  - it will apply/invoke a function inside "{}"
      //  - and returns result of "{}" as future

      // NOTE: f1 and f2 were already started at/before the moment we reach block  #2
      // So, NO f2 work does not depend on f1 - they all works on asynchronous.
      // what is synchronous/sequential is flow itself:
       // first it waits for f1 to complete then for f2 to complete.

    }
    future3 foreach {v =>
      println("v2: " + v)
    }
  }

  // same as this:
  // #3. for-comprehension (more readable)
  {

    val future3: Future[Int] =              // 'for' uses future's 'flatMap'
      for { x <- f1
           y <- f2
      } yield  x + y

    future3 foreach {v =>
      println("v3: " + v)
    }
  }

  // #4. Because map/flatMap/for works with the futures that is already started
  // it s really BAD idea to pass a function that returns Future but not Future itself
  // like this:
  {

    def getF1() = {
      Future {
        blocking {
          println("1")
          Thread.sleep(3000) // let's say this guys is slow ..
          1
        }
      }
    }

    def getF2() = {
      Future {
          println("2")  // will print "2' only after "1" is printed (even though "2' is faster)
          2
      }
    }

    val future3: Future[Int] =
      for { x <- getF1()  //
            y <- getF2()  // the computation of F2 will start after getF1 is completed !!
      } yield  x + y      // 'for' itself is sequential after all. it is still 'for' !

    // YES, now all is sequential and we loose that advantage to be asynchronous
    // Now: Computation in F

    future3 foreach { v =>
      println("v4: " + v)
    }
  }
  // --
  Thread.sleep(4000)
}