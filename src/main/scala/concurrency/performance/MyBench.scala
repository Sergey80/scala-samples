package concurrency.performance

import concurrency.logging.threadfactory.CustomThreadFactory
import org.scalameter._

import java.util.concurrent.ForkJoinPool
import scala.concurrent.{Await, Future, blocking}
import scala.concurrent.duration._

// ForkJoinPool: https://en.wikipedia.org/wiki/Fork%E2%80%93join_mode

// In this example we show how much schedulingEcCtx is involved
// For example here we see the stats that shows that even though all work is done workPool / workerEcCtx

// but the schedulingPool/schedulingEcCtx is used in the Future.sequence(statsF),
// where it waits until all features have completed, and it also takes effort
// we see that schedulingPool will use all cores and will use many "steals" (about 2383)

// (https://en.wikipedia.org/wiki/Work_stealing)

// stealing (work stealing scheduler) - reusing existing threads in the pool

// "a virtue of employing work-stealing: all threads in the pool attempt to find and execute 'subtasks' created by other 'active' tasks (eventually blocking waiting for work if none exist)"

// But if we change 'schedulingPool' size to have, say 40 threads,
// then will see that actually it would use only 16 of them,
// keep stealing factor about 100 steals

object MyBench extends App {

  val workPool = new ForkJoinPool(4, CustomThreadFactory("work"), CustomThreadFactory.uncaughtExceptionHandler, true)
  val workerEcCtx = scala.concurrent.ExecutionContext.fromExecutor(workPool)

  val schedulingPool = new ForkJoinPool(4, CustomThreadFactory("work"), CustomThreadFactory.uncaughtExceptionHandler, true)
  val schedulingEcCtx = scala.concurrent.ExecutionContext.fromExecutor(schedulingPool)

  /**
   * @param active  - The pool attempts to maintain enough active (or available) threads by dynamically adding, suspending, or resuming internal worker threads
   * @param running - running threads/tasks (supposed to be max: 32767)
   * @param steals  - amount of stealing tasks
   * @param tasks   - number of tasks submitted to this pool
   * @param submissions -  number of tasks submitted to this pool that have not yet begun executing
   */
  case class Stat(active:Int, running:Int, steals:Long, tasks:Long, submissions:Int)

  var statPairs : Seq[(Stat, Stat)] = _

  val m = measure {

    val statsF = (1 to 20000) map { n => // a lot of work to do
      Future {
        blocking {
          Thread.sleep(5000) // work takes some time

          (
          
            Stat( // _1
              active =      workPool.getActiveThreadCount,
              running =     workPool.getRunningThreadCount,
              steals =      workPool.getStealCount,
              tasks =       workPool.getQueuedTaskCount,
              submissions = workPool.getQueuedSubmissionCount
            ),

            Stat( // _2
              active =      schedulingPool.getActiveThreadCount,
              running =     schedulingPool.getRunningThreadCount,
              steals =      schedulingPool.getStealCount,
              tasks =       schedulingPool.getQueuedTaskCount,
              submissions = schedulingPool.getQueuedSubmissionCount
            )
          )

        }
      }(workerEcCtx)
    }

    implicit val _schedulingEcCtx = schedulingEcCtx
    val f = Future.sequence(statsF)

    statPairs = Await.result(f, 60.seconds)

  }

  println (m.value/1000 + " sec")

  println("workPool's stat[active, running, steals, tasks, submission] max:  " + maxStat(statPairs)(to_1))
  println("workPool's stat[active, running, steals, tasks, sSubmission] av:  " + average(statPairs)(to_1))

  println(" --- ")

  println("schedulingPool's stat[active, running, steals, tasks, submission] max:  " + maxStat(statPairs)(to_2))
  println("schedulingPool's stat[active, running, steals, tasks, submission] av:  " + average(statPairs)(to_2))

  println(" --- ")

  println("Work pool size: " + workPool.getPoolSize)
  println("Scheduling pool size: " + schedulingPool.getPoolSize) // should not be more than 4


  // stats function

  def maxStat( statsPair:Seq[(Stat, Stat)]) (f: Seq[(Stat, Stat)] => Seq[Stat] ) :Stat = {
    val stats = f(statsPair)
    Stat (
      stats.map(_.active).max,
      stats.map(_.running).max,
      stats.map(_.steals).max,
      stats.map(_.tasks).max,
      stats.map(_.submissions).max
    )
  }

  def average( statsPair:Seq[(Stat, Stat)]) (f: Seq[(Stat, Stat)] => Seq[Stat] ) :Stat = {
    val stats = f(statsPair)
    Stat (
      stats.map(_.active).sum / stats.size,
      stats.map(_.running).sum / stats.size,
      stats.map(_.steals).sum / stats.size,
      stats.map(_.tasks).sum / stats.size,
      stats.map(_.submissions).sum / stats.size
    )
  }

  def to_1(pairStat: Seq[(Stat, Stat)]) : Seq[Stat] =  statPairs map {s => s._1}
  def to_2(pairStat: Seq[(Stat, Stat)]) : Seq[Stat] =  statPairs map {s => s._2}

}

/*

  Possible output: 4 cores:

  24.125743104 sec

  workPool's stat[active, running, steals, tasks, submission] max:  Stat(762,7806,19973,0,11359)
  workPool's stat[active, running, steals, tasks, sSubmission] av:  Stat(252,2808,8101,0,5026)
   ---
  schedulingPool's stat[active, running, steals, tasks, submission] max:  Stat(4,3,986,1,1)
  schedulingPool's stat[active, running, steals, tasks, submission] av:  Stat(0,0,103,0,0)
   ---
  Work pool size: 9705
  Scheduling pool size: 4

*/