package performance

// https://scalameter.github.io/home/gettingstarted/0.7/executors/

object BenchmarksSimple extends App {

  import org.scalameter._

  //

  val quantity: Quantity[Double] = config {

    Key.exec.benchRuns -> 100
    Key.verbose -> false

  } withWarmer {

    new Warmer.Default

  } withMeasurer {

    //new Measurer.Default                // time
    //new Measurer.IgnoringGC           // time without GC cycles
    new Measurer.MemoryFootprint      // memory

  } measure {
                // creating many SimpleDateFormat objects
    for {
      i <- 0 to 20000
    } yield new java.text.SimpleDateFormat("yyyy/MM/dd")

  }

  println(s"Used quantity: ${quantity}")  // depends on 'withMeasurer'

  // Outputs like:
  // Used quantity: 24.727337 ms
  // Used quantity: 18599.848 kB

}