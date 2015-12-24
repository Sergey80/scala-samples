package concurrency.apptmpl.services

import java.util.concurrent.{Executors, Executor}

object Services {

   implicit val blockingExContext = scala.concurrent.ExecutionContext.fromExecutor(null: Executor)

}

