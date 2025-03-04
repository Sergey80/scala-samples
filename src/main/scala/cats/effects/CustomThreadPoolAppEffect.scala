import cats.effect.{IO, IOApp, Resource}
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

object FullThreadControlApp extends IOApp.Simple {

  // Custom execution contexts
  val blockingEc: Resource[IO, ExecutionContext] =
    Resource.make { //  safely acquires and release resources like files, database connections, network sockets, etc..
      IO(ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(4)))
    } { ec => IO(ec.shutdown()) }

  // Blocking task
  def blockingTask(ec: ExecutionContext): IO[Unit] =
    IO.blocking {
      println(s"[BLOCKING] Running on ${Thread.currentThread().getName}")
      Thread.sleep(2000)
      println(s"[BLOCKING] Finished on ${Thread.currentThread().getName}")
    }.evalOn(ec)

  // CPU-bound task
  import scala.concurrent.duration.DurationInt

  val computeTask: IO[Unit] =
    IO.println(s"[COMPUTE] Running on ${Thread.currentThread().getName}") *>
      IO.sleep(1.second) *>
      IO.println(s"[COMPUTE] Finished on ${Thread.currentThread().getName}")

  // Run everything
  val program: IO[Unit] = blockingEc.use { ec =>
    for {
      fiber1 <- computeTask.start  // Uses default compute pool
      fiber2 <- blockingTask(ec).start // Uses our custom blocking pool
      _ <- fiber1.join
      _ <- fiber2.join
    } yield ()
  }

  override def run: IO[Unit] = program
}

/**
 * Result:
 * [COMPUTE] Running on main
 * [BLOCKING] Running on io-blocking-0
 * [COMPUTE] Finished on main
 * [BLOCKING] Finished on io-blocking-0
 */

// what the diff between this IO.blocking { and say execution context, no cats effect code?
/* import scala.concurrent.{ExecutionContext, Future}
  import java.util.concurrent.Executors

  // Define a fixed thread pool
  val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(4))

  // Run blocking work manually
  val futureTask: Future[String] = Future {
    Thread.sleep(2000) // Blocking!
    s"Completed on thread: ${Thread.currentThread().getName}"
  }(ec)

  futureTask.foreach(println)(scala.concurrent.ExecutionContext.global) // Print result
*/

//✅That no cat's effect approach is:
//
//  + Runs on a separate thread pool
//  ❌ Still blocking the assigned thread—it’s just on a different pool.
//  ❌ No structured concurrency—you must manage cancellation yourself.
//  ❌ Not composable—you can't easily mix blocking + async safely.
