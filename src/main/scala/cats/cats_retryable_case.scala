//> using scala "3.3.0"
//> using lib "com.github.cb372::cats-retry:3.1.0"
//> using lib "org.typelevel::cats-effect:3.5.1"

import cats.implicits._
import cats.effect.{IO, IOApp}
import retry.RetryPolicies._
import retry._
import scala.concurrent.duration._

object ApiRetryExample extends IOApp.Simple {

  // Simulated unstable API call (fails the first 3 times, succeeds on 4th)
  var attemptCount = 0
  def callUnstableApi: IO[String] = IO {
    attemptCount += 1
    if (attemptCount <= 3) {
      // Fail with an exception for the first 3 attempts
      throw new RuntimeException("API failed")
    } else {
      // Succeed on the 4th attempt
      "API Response"
    }
  }

  // Define a retry policy: up to 3 retries with 1-second delay between attempts
  val retryPolicy = limitRetries[IO](3) |+| constantDelay[IO](1.second)

  // Error handler: log each retry attempt
  def onError(err: Throwable, details: RetryDetails): IO[Unit] =
    IO.println(s"Retrying due to: ${err.getMessage}")

  // Wrap the API call with retry logic to automatically retry on any error
  val retryingCall: IO[String] = retryingOnAllErrors[String](retryPolicy, onError)(callUnstableApi)

  // Entry point: execute the call and print the final result
  override def run: IO[Unit] =
    retryingCall.flatMap(response => IO.println(s"Final Response: $response"))
}
