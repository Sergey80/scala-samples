package concurrency.apptmpl.managers

import java.util.concurrent.{Executors, Executor}

object Managers {

  // Manager(s) has deal with slow/'blocking'-operations like IO/Net so we use separate execution context for its work

  implicit val waitingCtx = scala.concurrent.ExecutionContext.fromExecutor(null: Executor)
  //implicit val waitingCtx = scala.concurrent.ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))

  //implicit val waitingCtx = scala.concurrent.ExecutionContext.fromExecutor(Executors.newCachedThreadPool())
}
