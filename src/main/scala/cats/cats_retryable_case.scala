// As we saw in the previous example, it was questionable whether we needed Cats at all.
// It seemed like an overkill—we could have achieved the same result with simple Scala or even TypeScript.
// But now, as we progress, we begin to see why it's not exactly overkill.
// Looking at the retry example, we realize where Cats truly shines.

// --- So, why is cats-retry better than a simple manual retry? ---

/**
 * A naive approach: Manually implementing retries.
 *
 * The following function attempts to retry a failing task a given number of times.
 */
def retryManually[A](task: => IO[A], maxRetries: Int): IO[A] = {
  task.handleErrorWith { err =>
    if (maxRetries > 0) {
      println(s"Retrying due to: ${err.getMessage}")
      retryManually(task, maxRetries - 1)
    } else {
      IO.raiseError(err)
    }
  }
}

/*
 * 🚨 Problems with this approach:
 *
 * ❌ No built-in delay handling → We must manually add `Thread.sleep`, blocking execution.
 * ❌ No flexible retry strategies → We must manually code backoff, jitter, and retry limits.
 * ❌ Not composable → Harder to integrate with Cats abstractions like `Resource` or `Parallel` execution.
 */

import cats.implicits._
import cats.effect.{IO, IOApp}
import retry.RetryPolicies._
import retry._
import scala.concurrent.duration._

object ApiRetryExample extends IOApp.Simple {

  // 🎭 Simulated unstable API call (fails the first 3 times, succeeds on 4th attempt)
  var attemptCount = 0
  def callUnstableApi: IO[String] = IO {
    attemptCount += 1
    if (attemptCount <= 3) {
      throw new RuntimeException("API failed")
    } else {
      "API Response"
    }
  }

  // 🛠 Define a retry policy: Up to 3 retries, with a 1-second delay between attempts
  val retryPolicy = limitRetries[IO](3) |+| constantDelay[IO](1.second)

  // 📌 Error handler: Logs each retry attempt
  def onError(err: Throwable, details: RetryDetails): IO[Unit] =
    IO.println(s"Retrying due to: ${err.getMessage}")

  // 🚀 Wrapping the API call with retry logic
  val retryingCall: IO[String] = retryingOnAllErrors[String](retryPolicy, onError)(callUnstableApi)

  // 🔥 Entry point: Execute the call and print the final result
  override def run: IO[Unit] =
    retryingCall.flatMap(response => IO.println(s"Final Response: $response"))
}

/*
 * ✨ Why Cats-Retry Shines ✨
 *
 * ✅ Declarative → Define retry logic separately from business logic.
 * ✅ Composable → Works seamlessly with Cats-Effect (IO), Resource, and FP constructs.
 * ✅ Flexible → Supports exponential backoff, jitter, time limits, and custom rules out-of-the-box.
 * ✅ Non-blocking → Avoids `Thread.sleep`; everything runs asynchronously.
 */

/*
  And yet, it maybe not enought, think twice, if you tink scala + casts introduce more complexity to your code,
  the code, that very soon will lose its value, because I is to be written by AI, not people
  (for people is to know what to write, to know the user stories), people will be read it,
 then ask yourse this: what language would be the best for you as human to read the code

 Consider this TS code for example:

https://medium.com/@sergii_54085/choosing-the-right-abstraction-449d7b9b1143

*/