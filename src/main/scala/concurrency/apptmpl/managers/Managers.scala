package concurrency.apptmpl.managers

import java.util.concurrent.{Executors, Executor}

object Managers {

  // 1.
  // same as 'global' ex context
  //implicit val waitingCtx = scala.concurrent.ExecutionContext.fromExecutor(null: Executor)

  // 2.
  //implicit val waitingCtx = scala.concurrent.ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))
  implicit val waitingCtx = scala.concurrent.ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))

  // 3.
  // Creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they are available.
  //implicit val waitingCtx = scala.concurrent.ExecutionContext.fromExecutor(Executors.newCachedThreadPool())
}
