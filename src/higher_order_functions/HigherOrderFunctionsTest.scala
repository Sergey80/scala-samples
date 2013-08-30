package higher_order_functions

// http://blog.bruchez.name/2011/10/scala-partial-functions-without-phd.html

// Higher Order Functions - These are functions that take other functions as parameters, or whose result is a function

object HigherOrderFunctionsTest extends App {

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
  var1 = 0

  println( takeFunction2( fnGen(), 2, 2) )
  println(var1)


}


