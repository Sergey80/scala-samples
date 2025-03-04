import weaver._
import cats.effect.IO

import weaver._
import cats.effect.{IO}
import scala.concurrent.duration._

object ParallelTest extends SimpleIOSuite {
  test("test 1") { _ =>
    IO.println("Starting test 1") *>
      IO.sleep(2.seconds) *>
      IO.println("Finished test 1") *>
      IO.pure(expect(1 + 1 == 2))
  }

  test("test 2") { _ =>
    IO.println("Starting test 2") *>
      IO.sleep(2.seconds) *>
      IO.println("Finished test 2") *>
      IO.pure(expect(2 + 2 == 4))
  }
}


