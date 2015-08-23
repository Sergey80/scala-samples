package concurrency

import org.joda.time.{DateTime}
import org.joda.time.format.{DateTimeFormat}

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Await, Future, blocking}

/**
 * As story goes,
 * Once upon a time there were 2*cores parents in the "Pride of one Pool".
 *
 * The parents were big figure in the world and they could finish many tasks in ~1 second,
 * even if they work together in parallel they were smart enough to create a new space for their
 * work or reuse remaining space left after another parent has done with his/her work.
 *
 * Only windage (the retarding force of air friction on ther move) could stop them to be even faster.
 *
 * That happens that each of them would make a birth to another 10 children,
 * who were not as efficient as their parents - they were not capable to create new space/playground
 * for themself to play there, but rather they played in limited cores-long pool.
 *
 * Also those children were not as fast as their parents -
 * they could barely finish simple task in 2 seconds (not in 1 as their parents)
 *
 * One moment at time, when a great danger has come, and it requires all parent to make a grate job
 * to defence their One Pool Pride in one (or at least two seconds)
 * that happens that they could not make it happen.
 *
 * So all were dead due this failure.
 *
 * And then after, when the historians have come, they have grabbed the log from Pride's behaviour.
 * A that moment there were 8 parents (and 80 childs respectively):
 *
> [40:08 : 03] parent: ForkJoinPool-1-worker-7 has finished the action
> [40:08 : 03] parent: ForkJoinPool-1-worker-13 has finished the action
> [40:08 : 03] parent: ForkJoinPool-1-worker-11 has finished the action
> [40:08 : 03] parent: ForkJoinPool-1-worker-3 has finished the action
> [40:08 : 03] parent: ForkJoinPool-1-worker-5 has finished the action
> [40:08 : 03] parent: ForkJoinPool-1-worker-1 has finished the action

> child: ForkJoinPool-1-worker-15 of ForkJoinPool-1-worker-7 parent
> child: ForkJoinPool-1-worker-7 of ForkJoinPool-1-worker-7 parent
> child: ForkJoinPool-1-worker-13 of ForkJoinPool-1-worker-13 parent
> child: ForkJoinPool-1-worker-11 of ForkJoinPool-1-worker-11 parent
> child: ForkJoinPool-1-worker-3 of ForkJoinPool-1-worker-3 parent
> child: ForkJoinPool-1-worker-5 of ForkJoinPool-1-worker-5 parent
> child: ForkJoinPool-1-worker-1 of ForkJoinPool-1-worker-1 parent

 * Here we can see that only 6 parents could finish their work
 * Then a Childs start taking their actions
 * First child:   took thread #15 (free thread, good boy!)
 * Second child:  took thread #7 (the thread that was just released by the parent)
 * Third child:   took thread #13 (the thread that was just released by the parent)
 * Forth child:   took thread #11 (the thread that was just released by the parent)
 * Fifth child:   tool thread #3 (the thread that was just released by the parent)
 * Sixth child:   took thread #5 (the thread that was just released by the parent)
 * Seventh child: took thread #1  (the thread that was just released by the parent)

 * At this point we saw that 7 of 6 first childs took the threads that previously were owned
 * by parents. Children prefer to play on parent's place!

 * What thread the next child would prefer to take? Let's see:

> child: ForkJoinPool-1-worker-15 of ForkJoinPool-1-worker-13 parent

 Ah! Good boy has finished his play and now another child took over this thread!

* Nex?

>  child: ForkJoinPool-1-worker-7 of ForkJoinPool-1-worker-7 parent

One thread #7 (never been taken by parents)

Reminding that the children do not suppose to take more than 4 threads (at a time)
in the pool (since we have only 4 cores, and it is the default of our Execution context for childrent).

So far:
  #15, #7 - are unique threads that were taken only for children
  and # #13 #11 #3 #1 #7 - are thread that we belonging to the parents

  So, children took over 7 thread already (in summary). But where the parents?
  5 of 7 only finished their work?

 > ....

* After some time (16 second !! after the start):

> child: ForkJoinPool-1-worker-15 of ForkJoinPool-1-worker-11 parent
> parent: ForkJoinPool-1-worker-15 started an action
> [56:08 : 03] parent: ForkJoinPool-1-worker-15 has finished the action

Here we se that 'parent' could take thread #15 just after child has released it

But why parents did not created new treads for themself to operate on?
They were all marked as "blocking" didn't they suppose to be smart and fast?

...

* At that moment as far as Historians understood that a the moment of great defence
* the childs start using parents "space-time continuum" (threads) to play there..
* that would stop parents defence action (surprisingly),
* they/parents seem forget that they could create new threads when needed.
*
* Thus the parent could not defend the
* One Pool Pride until a child stop playing 2 second game there.
*
* As a result all were dead. The Evil won.
*
* That is sad story. I know.
*/

object NestedFutures extends App {

  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global
  val cores = Runtime.getRuntime.availableProcessors

  val works: Seq[Future[Seq[Future[String]]]] =

    (1 to cores * 2) map { x =>

      Future {
        blocking { // will create new thread/place if needed

          val parentName = Thread.currentThread.getName

          println("parent: " + parentName + " started an action")

    // bad outcome:
    // if all sleeps are commented (no work is taken by parents) - that will lead long-run work for parents

    // good outcome:
    //Thread.sleep(1000) // if we use this sleep ONLY (then all parents will finish their work in 1 sec)

          // being in 'blocking' we start more nested futures
          // will those future make use of parent 'blocking' and make use of new threads,
          // will they create new threads?
          // Will amount of this new thread be more than amount of cores?

          val playFutureOutcomes: Seq[Future[String]] = (1 to 10) map {stuffId =>
            childPlay(parentName = parentName)
          }


    // defense:
    // bad outcome:

    Thread.sleep(1000)  // that sleep here (represents an addition work to do for the parent),
                              // (after those nested/child futures were sent to execute,)
                              // brings us to interesting point:

          println(s"[${timeStamp()}] parent: " + parentName + " has finished the action")


          // If not the childs, all that parents thread would have finished themself in 1 second.

         // (and we expect parents to finish themself in 1 one second regardless the child existence)

         // But because each new child is looking for new thread (in the pool) to execute itslef,
         // and this new child is not intended to create new thread for itself but rather choose
           // one from limited pool from global execution context
           // (which is limited to amount of 'cores') - because child's Future is not marked with "blocking",

         // So it happens that a child would try first to take a parent one/thread to execute itlsef there
         // that would stop parent to do whatever he (or she) was intended to do,


          playFutureOutcomes
        }
      }
  }


  val result1: Seq[Seq[Future[String]]] = Await.result(Future.sequence(works), Duration.Inf)

  println("parents are done their work")

  val result2: Seq[String] = Await.result(Future.sequence(result1.flatten), Duration.Inf)

  println("result:")
  result2 foreach println

  def childPlay(parentName:String)(implicit ex:ExecutionContext):Future[String] = {
    Future {
      Thread.sleep(2000) // two seconds play session
      val threadName = Thread.currentThread.getName
      // log
      println("child: " + threadName + " of " + parentName + " parent")
      Thread.currentThread.getName
    }
  }

  def timeStamp(pattern:String = "ss:mm : hh"): String = {
    val fmt = DateTimeFormat.forPattern(pattern)
    fmt.print(DateTime.now)
  }


}
