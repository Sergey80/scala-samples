package concurrency.logging

import scala.concurrent.Future
import scala.concurrent.blocking

// https://www.igvita.com/2012/02/29/work-stealing-and-recursive-partitioning-with-fork-join/

/**
 * Created by serge on 18/09/15.
 *
 * Shows some useful statistics about scheduling ex context / pool
 * like how many threads are in use when it works with "for"/"map"/"flatMap"
 */
object SchedulingPoolStats extends App {

  case class Stat(size:Int, active:Int, running:Int, stealing:Long)

  def secondLongWork(): Future[String] = {
    Future {
      blocking {
        Thread.sleep(1000)
        println("done")
        "X"
      }
    }(Services.bockingWorkEx)
  }

  implicit val ex = Services.schedulingEx

  println(Services.schedulingPool)

println("------ 1 longWork --- ")

  val result1 = for {
               v1 <- secondLongWork()
  } yield v1

  printStats(1000)

println("------ 2 longWork --- ")

  val result2 = for {
    v1 <- secondLongWork()
    v2 <- secondLongWork()
  } yield v1 + v2

  printStats(2000)


println("------ 3 longWork --- ")

  val result3 = for {
    v1 <- secondLongWork()
    v2 <- secondLongWork()
    v3 <- secondLongWork()
  } yield v1 + v2 + v3

  printStats(3000)


println("------ 4 longWork --- ")

  val result4 = for {
    v1 <- secondLongWork()
    v2 <- secondLongWork()
    v3 <- secondLongWork()
    v4 <- secondLongWork()
  } yield v1 + v2 + v3 + v4

  printStats(4000)

println("------ 5 longWork --- ")

  val result5 = for {
    v1 <- secondLongWork()
    v2 <- secondLongWork()
    v3 <- secondLongWork()
    v4 <- secondLongWork()
    v5 <- secondLongWork()
  } yield v1 + v2 + v3 + v4 + v5

  printStats(5000)

  println("------ 5_1 longWork --- ")

  val result5_1 = for {
    v1 <- secondLongWork()
    v2 <- secondLongWork()
    v3 <- secondLongWork()
    v4 <- secondLongWork()
    v5 <- secondLongWork()
  } yield v1 + v2 + v3 + v4 + v5

  val result5_2 = for {
    v1 <- secondLongWork()
    v2 <- secondLongWork()
    v3 <- secondLongWork()
    v4 <- secondLongWork()
    v5 <- secondLongWork()
  } yield v1 + v2 + v3 + v4 + v5

  val result5_3 = for {
    v1 <- secondLongWork()
    v2 <- secondLongWork()
    v3 <- secondLongWork()
    v4 <- secondLongWork()
    v5 <- secondLongWork()
  } yield v1 + v2 + v3 + v4 + v5

  val result5_4 = for {
    v1 <- secondLongWork()
    v2 <- secondLongWork()
    v3 <- secondLongWork()
    v4 <- secondLongWork()
    v5 <- secondLongWork()
  } yield v1 + v2 + v3 + v4 + v5


  val result5_5 = for {
    v1 <- secondLongWork()
    v2 <- secondLongWork()
    v3 <- secondLongWork()
    v4 <- secondLongWork()
    v5 <- secondLongWork()
  } yield v1 + v2 + v3 + v4 + v5


  printStats(5000)

  //

  def printStats(durationMs:Int): Unit = {
    val stats = (0 to durationMs) map { x =>
      Thread.sleep(1)
      Stat (
        size = Services.schedulingPool.getPoolSize,
        active = Services.schedulingPool.getActiveThreadCount,
        running = Services.schedulingPool.getRunningThreadCount,
        stealing = Services.schedulingPool.getStealCount
      )
    }

    val maxSize = stats.map {stat =>
      stat.size
    }.max

    val maxActive = stats.map {stat =>
      stat.active
    }.max

    val maxRunning = stats.map {stat =>
      stat.running
    }.max

    val maxStealing = stats.map {stat =>
      stat.stealing
    }.max

    println("max stat[size,active,running,stealing]: " + Stat(maxSize, maxActive, maxRunning, maxStealing) )
  }

}

// Output: here we can see that 'stealing' goes up but poolSize and rest stays = 1
/*
scala.concurrent.forkjoin.ForkJoinPool@79f9805f[Running, parallelism = 4, size = 0, active = 0, running = 0, steals = 0, tasks = 0, submissions = 0]
------ 1 longWork ---
done
max stat[size,active,running,stealing]: Stat(1,1,1,1)
------ 2 longWork ---
done
done
max stat[size,active,running,stealing]: Stat(1,1,1,3)
------ 3 longWork ---
done
done
done
max stat[size,active,running,stealing]: Stat(1,1,1,6)
------ 4 longWork ---
done
done
done
done
max stat[size,active,running,stealing]: Stat(1,1,1,10)
------ 5 longWork ---
done
done
done
done
done
max stat[size,active,running,stealing]: Stat(1,1,1,15)

*/

// But for 5_1 scenario:

// it will use all pool size(4), all thread will be active and running: 4

// max stat[size,active,running,stealing]: Stat(4,4,4,45)
