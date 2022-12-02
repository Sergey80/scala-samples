package concurrency.logging.threadfactory

import java.lang.Thread.UncaughtExceptionHandler
import java.util.concurrent.{ForkJoinPool, ForkJoinWorkerThread, ThreadFactory}
import scala.concurrent.{BlockContext, CanAwait}

// The purpose if this factory is to modify tread name, putting a prefix before actual name.
// that facilitate debugging and logging in async env

class CustomThreadFactory(prefix: String, uncaughtExceptionHandler:UncaughtExceptionHandler) extends ThreadFactory with ForkJoinPool.ForkJoinWorkerThreadFactory {
  def wire[T <: Thread](thread: T): T = {
    thread.setDaemon(true)
    thread.setUncaughtExceptionHandler(uncaughtExceptionHandler)
    thread.setName(prefix + "-" + thread.getName)  // prefix here !
    thread
  }

  def newThread(runnable: Runnable): Thread = wire(new Thread(runnable))

  def newThread(fjp: ForkJoinPool): ForkJoinWorkerThread = wire(new ForkJoinWorkerThread(fjp) with BlockContext {
    override def blockOn[T](thunk: =>T)(implicit permission: CanAwait): T = {
      var result: T = null.asInstanceOf[T]
      ForkJoinPool.managedBlock(new ForkJoinPool.ManagedBlocker {
        @volatile var isdone = false
        override def block(): Boolean = {
          result = try thunk finally { isdone = true }
          true
        }
        override def isReleasable = isdone
      })
      result
    }
  })
}

object CustomThreadFactory {

  def apply(prefix:String): CustomThreadFactory = {
    new CustomThreadFactory(prefix, uncaughtExceptionHandler)
  }

  val uncaughtExceptionHandler = new UncaughtExceptionHandler {
    override def uncaughtException(t: Thread, e: Throwable) = e.printStackTrace()
  }


}
