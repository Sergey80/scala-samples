package concurrency.performance

import concurrency.logging.threadfactory.CustomThreadFactory
import org.scalameter._

import scala.concurrent.{Future, Await, blocking}
import scala.concurrent.duration._

object MyBench extends App {

  val workPool = new scala.concurrent.forkjoin.ForkJoinPool(4, CustomThreadFactory("work"), CustomThreadFactory.uncaughtExceptionHandler, true)
  val workerEcCtx = scala.concurrent.ExecutionContext.fromExecutor(workPool)

  val schedulingPool = new scala.concurrent.forkjoin.ForkJoinPool(4, CustomThreadFactory("work"), CustomThreadFactory.uncaughtExceptionHandler, true)
  val schedulingEcCtx = scala.concurrent.ExecutionContext.fromExecutor(schedulingPool)

  case class Stat(active:Int, running:Int, steals:Long, tasks:Long, submissions:Int)

  var statPairs : Seq[(Stat, Stat)] = _

  val m = measure {

    val statsF = (1 to 20000) map { n =>
      Future {
        blocking {
          Thread.sleep(5000)

          Pair(
          
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


  //
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