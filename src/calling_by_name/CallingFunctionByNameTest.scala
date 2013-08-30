package calling_by_name

// #calling by name - arguments that are evaluating on the moment of use, but not on the moment of passing them to function.
// #partially applied function - use "_" to omit some arguments and return a new function instead that expects rest of not-yet-applied arguments to be passed to that new function
// #higher order function - take function as parameter

// Calling function By Name

object CallingFunctionByNameTest extends App {

  var var1 = 0

  // 1.
  // the function that accepts arg-function with: two int params and returning String
  // the function passing v1 & v2 as parameters to arg-function, invoking arg-function 2 times, connecting the result to one string
  def takeFunction1(f: (Int, Int) => String, v1:Int, v2:Int ): String = {
    f(v1, v2) + f(v1, v2)
  }

  // 2. same as #1 but calling arg-function by-name
  def takeFunction2(f: => ((Int, Int) => String), v1:Int, v2:Int): String = {
    f(v1, v2) + f(v1, v2)
  }

  /* nGen does not have any parameters defined (on first glance),
   * but since fnGen returns 'partially applied function' which made by using "_" on aFun,
   * then it makes fnGen returns the function that's able to apply parameters
  */
  def fnGen() = {

    var1 += 1

    def aFun(v1:Int, v2:Int) : String = {
      (v1 + v2).toString
    }

    aFun _

  }

  // --


  println( takeFunction1( fnGen(), 2, 2) )
  println(var1)

  println( takeFunction2( fnGen(), 2, 2) )
  println(var1)

  /* Output:
  44
  1
  44
  3
  */


}


