package calling_by_name

// Calling parameter "By Name"
// See also: CallingFunctionByName

/*
  #calling-by-name
  related: #calling-by-value #lazy-evaluation
*/
// sequence: #1 (look at #2 after)

object CallingByNameTest extends App {

  def fByValue(x:Int, y:Int) = {

    if (x < 0) y else x // if x < 0 we are not going to use "y"

  }

  def fByName(x:Int, y: => Int) = {

    if (x < 0) y else x   // if we are not going to use "y", not need to trigger it to evaluate

  }

  def y() = {println ("y is calling"); 1} // will print the text by calling

  val result1 = fByValue( x=0, y=y() ) // y() is evaluating by passing .. (#calling-by-value related) but nobody is going to use its value though
  val result2 = fByName ( x=0, y=y() ) // y() will not be evaluated (#lazy-evaluation related)

  println (result1)
  println (result2)

  /*
  * Output:
  * y is calling
  * 0
  * 0
  * */

}
