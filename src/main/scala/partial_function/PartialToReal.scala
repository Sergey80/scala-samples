package partial_function

// please look at 'PartialFunctionTest' first

// #partial-function #lift

object PartialToReal extends App {

  type PF = PartialFunction[Int, Int] // just to make it shorter

  val pf1 : PF = { case 1 => 2 }

  println ( pf1 )  // <function1>


  // jut check that we on the right way
  val defined = pf1.isDefinedAt(1)
  println ("defined: " + defined)  // true


  val realFunction = pf1.lift  // convert it to real function !


  println ( realFunction(1) )           // Some(2)


}
