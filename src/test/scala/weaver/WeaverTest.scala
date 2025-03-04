import weaver._
import cats.effect.IO

object WeaverTest extends SimpleIOSuite {
  println("This runs immediately!")  // Side effect runs before tests!

  test("test with println") { _ =>
    println("Test println!")  // Runs immediately, before test even executes
    IO.pure(expect(true))
  }

  test("test with IO.println") { _ =>
    IO.println("Test IO.println!") *> IO.pure(expect(true))
  }

  test("test with IO") { _ =>
    import scala.concurrent.duration.DurationInt

    // With IO.println:
    //
    //The print statement executes in the correct order after sleeping.
    //You can compose it safely with other IO effects.

    IO.sleep(2.seconds) *> IO.println("Done sleeping!") *> IO.pure(expect(true))
  }

  test("test with IO 2") { _ => // still - in parallel, respecting its own sleep pause
    import scala.concurrent.duration.DurationInt

    // With IO.println:
    //
    //The print statement executes in the correct order after sleeping.
    //You can compose it safely with other IO effects.

    IO.sleep(2.seconds) *> IO.println("Done sleeping! 2") *> IO.pure(expect(true))
  }

}
