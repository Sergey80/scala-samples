package partial_function

// please look at 'PartialFunctionTest' first

// #partial-function #lift

object PartialToReal extends App {

  // # 1
  {
    type PF = PartialFunction[Int, Int] // just to make it shorter

    val pf1 : PF = { case 1 => 2 }

    println ( "pf2: " + pf1 )  // <function1>


    // jut check that we on the right way
    val defined = pf1.isDefinedAt(1)
    println ("defined: " + defined)  // true


    val realFunction = pf1.lift  // convert it to real function !


    println ( "realFunction(1): " + realFunction(1) )           // Some(2)

  }

  // #2
  {

    val level =
      """ooo-------
        |oSoooo----
        |ooooooooo-
        |-ooooooooo
        |-----ooToo
        |------ooo-""".stripMargin

    lazy val vector: Vector[Vector[Char]] = Vector(level.split("\n").map(str => Vector(str: _*)): _*)


    case class Pos(x: Int, y: Int) {
      def dx(d: Int) = copy(x = x + d)
      def dy(d: Int) = copy(y = y + d)
    }

   def terrainFunction(levelVector: Vector[Vector[Char]]): Pos => Boolean = {

     case Pos(x,y) => {

        //println (s"x=$x; y=$y")

        (
          for {
            row <- levelVector lift(x)
            ch  <- row lift(y)
            if ch != '-'
          } yield ch

          ).isDefined

      }

   }

    lazy val terrain = terrainFunction(vector)


    println( "terrain: " + terrain lift (1) ) // Some(e) ???


  }


}
