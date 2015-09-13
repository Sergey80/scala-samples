package concurrency

import scala.concurrent.{ExecutionContext, Await, Future}

object MapFlatMapFor extends App {

  implicit val ex = ExecutionContext.Implicits.global   // will be used by map/flatMap/for

  val f1 = Future {1}
  val f2 = Future {2}

  // 1. map
  {
    val future3: Future[Future[Int]] = f1.map {x2 =>
      f2.map { x1 =>
        x1 + x2
      }
    }

    future3 foreach { (v: Future[Int]) =>  // not yet value (that why we use FlatMap in next step)
      println("v1: " + v) // Promise
    }

  }

  // 2. flatMap
  {
    val future3: Future[Int] = f1.flatMap { x => // flatMap[S](f: T => Future[S])
      f2.map(y => x + y)                         // map[S](f: T => S)
    }
    future3 foreach {v =>
      println("v1: " + v)
    }
  }

  // same as this:
  // 3. for-comprehension (more readable)
  {

    val future3: Future[Int] =              // 'for' uses future's 'flatMap'
      for { x <- f1
           y <- f2
      } yield  x + y

    future3 foreach {v =>
      println("v1: " + v)
    }
  }


  // --
  Thread.sleep(1000)

}
