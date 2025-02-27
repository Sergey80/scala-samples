package concurrency

import scala.concurrent.{ExecutionContext, Future, blocking}

// #future #map #flatMap #for-comprehension
object MapFlatMapFor extends App {
  implicit val ex: ExecutionContext = ExecutionContext.Implicits.global   // will be used by map/flatMap/for

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
  // Remove the redundant ExecutionContext definition
  // implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  {
    val future3: Future[Int] = f1.flatMap { x =>
      f2.map(y => x + y)
    }
    future3.foreach { v =>
      println("v2: " + v)
    }
  }

  {
    val future3: Future[Int] =
      for {
        x <- f1
        y <- f2
      } yield x + y

    future3.foreach { v =>
      println("v3: " + v)
    }
  }

  {
    def getF1() = {
      Future {
        blocking {
          println("1")
          Thread.sleep(3000)
          1
        }
      }
    }

    def getF2() = {
      Future {
          println("2")
          2
      }
    }

    val future3: Future[Int] =
      for {
        x <- getF1()
        y <- getF2()
      } yield x + y

    future3.foreach { v =>
      println("v4: " + v)
    }
  }

  Thread.sleep(4000)}