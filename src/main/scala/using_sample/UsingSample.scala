import cats.effect.{IO, IOApp}

// Demonstrating `using` and `given` in Scala 3 with Cats Effect

object UsingSample extends IOApp.Simple {

  // ✅ `sample1()`: Demonstrates `using`
  def sample1(): IO[Unit] = {
    def greet(name: String)(using language: String): String =
      s"Hello, $name! (Language: $language)"

    given String = "English" // `given` provides an implicit value for `using`

    for {
      message <- IO.pure(greet("Alice")) // Keep it inside IO
      _ <- IO.println(message)           // Print using IO
    } yield ()
  }

  // ✅ `sample2()`: Demonstrates `given` for Logging
  def sample2(): IO[Unit] = {
    trait Logger:
      def log(msg: String): IO[Unit] // ✅ Changed to IO[Unit]

    given ConsoleLogger: Logger with
      def log(msg: String): IO[Unit] = IO.println(s"[LOG] $msg") // ✅ Use IO.println

    def debug(msg: String)(using logger: Logger): IO[Unit] =
      logger.log(msg) // ✅ Returns IO instead of performing println immediately

    debug("Something happened!") // ✅ Returns IO[Unit] properly
  }

  // ✅ Run both `sample1()` and `sample2()` in sequence
  val program: IO[Unit] = for {
    _ <- sample1()
    _ <- sample2()
  } yield ()

  override def run: IO[Unit] = program
}
