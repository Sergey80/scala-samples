package concurrency.apptmpl.services

import java.util.concurrent.{Executors, Executor}

object Services {
   implicit val blockingExContext = scala.concurrent.ExecutionContext.fromExecutor(null: Executor)

  // Creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they are available.
  //implicit val blockingExContext = scala.concurrent.ExecutionContext.fromExecutor(Executors.newCachedThreadPool())
}

