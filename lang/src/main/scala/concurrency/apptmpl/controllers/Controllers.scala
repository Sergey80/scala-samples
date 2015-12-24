package concurrency.apptmpl.controllers

import java.util.concurrent.Executor

object Controllers {
  val ex1 = scala.concurrent.ExecutionContext.global

  val ex2 = scala.concurrent.ExecutionContext.fromExecutor(null: Executor)
}