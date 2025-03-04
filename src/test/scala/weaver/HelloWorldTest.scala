import weaver.SimpleIOSuite
import cats.effect.IO
import cats.implicits._

object HelloWorldTest extends SimpleIOSuite {
  test("basic test") { _ =>
    expect(1 + 1 == 2).pure[IO]
  }

  /***
   * How This Differs from ScalaTest
   * Feature	| Weaver (SimpleIOSuite)	| ScalaTest
   * ---------| --------------------    | -----------------------------------
   * Effectful Code	Uses IO, avoids blocking	Uses Future or blocks with Await.result(...)
   * Parallel Execution	Yes (out of the box)	No (unless explicitly configured)
   * Cats Effect Support	Built-in	Requires extra setup
   * Thread Safety	Safer, runs tests in fibers	Can block or create race conditions
   */
}
