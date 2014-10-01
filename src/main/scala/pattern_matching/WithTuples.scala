package pattern_matching

//#pattern-matching #tuples

object WithTuples extends App {

  // often I wrote matching like this:
  def function(arg1:String, arg2:String) : String = arg1 match { // matching by one arguments
    case "a" => "first letter"
    case _ => "not first letter"
  }

  // but what if we need match by several one ?
  def function2(arg1:String, arg2:String) : String = (arg1, arg2) match { // here we go- we use tuples
    case ("val1", "val2") => "val1, val2"
    case (a, b) if a == b => "equal!"   // use tuples to hit the case
  }

  val result = function2("a", "a") // equal!


  println(result)

}
