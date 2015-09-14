package concurrency.apptmpl.managers

import java.util.concurrent.{Executor}

object Managers {

  // same as 'global' ex context
  implicit val waitingCtx = scala.concurrent.ExecutionContext.fromExecutor(null: Executor)

  // fixed ex context
  //implicit val waitingCtx = scala.concurrent.ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))
}
