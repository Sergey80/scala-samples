package performance

import org.scalameter.Key

/**
 * the way to use/combine several measures at on(c)e
 */

object BenchmarksCombine extends App {

  import org.scalameter._

  /** 
   * @param  measuresMap - map of name -> measure
   * @param block - a block of code to execute,
   * @param confs - (optional) configuration, if not set default will be used
   * @return a name->Quantity map back  
   */

  def measures[S](measuresMap: Map[String, Measurer[Double]], confs: KeyValue* )
              (block: ()=> S ): Map[String, Quantity[Double]] = {

    measuresMap.map {

      case(name, aMeasure) => {

        val cs: Seq[KeyValue] = if(confs.nonEmpty) confs else {
          Array[KeyValue] {                   // deafult conf
            Key.exec.benchRuns -> 100
            Key.verbose -> false
          }
        }

      val quantity: Quantity[Double] = config (cs: _*).
          
       withWarmer {

          new Warmer.Default  // hardcoded

       } withMeasurer {

          aMeasure

       } measure {

          block()
       }

        (name , quantity)

      }
    }
  }

  val mm = measures (

    measuresMap = Map(
                      "time"   -> new Measurer.Default,
                      "memory" -> new Measurer.MemoryFootprint
                  )
    /*, confs = {                     // uncomment to pass specific params to replace default ones
      Key.exec.benchRuns -> 20
      Key.verbose -> true
    }*/
  
   )(

    block = { () =>

      for {
        i <- 0 to 20000
      } yield new java.text.SimpleDateFormat("yyyy/MM/dd") // btw never use that SimpleDateFormat, just don't

    }

  )

  println(s"Used quantities: ${mm}")  // depends on 'withMeasurer'

  // Output like:
  // Used quantities: Map(time -> 27.582114 ms, memory -> 18651.976 kB)
}
