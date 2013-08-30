package currying

/*
 * The difference between a Function that returns function and Currying -function that expect parameters,
  * but if those are missed - returns a function where those params are expected to be passed afterwords. Due to using "_".
 */
object CurryingTest extends App {

{ // 1. not Currying - it just returns a anonymous function with predefined body/algorithm
  def add1(a:Int) = { b:Int => a + b } // use anonymous function, that expect b:Int as param and use "a" from parent-function
  // 1.1
  println ("1.1: " + add1(5)(6))

  // 1.2
  //add1(5)_  // can not be treated as 'partially applied function',
  //            // because it there is no 2nd parameter defined, soo there is nothing to omit

   val f = add1(5) // not need to treat it as 'partially applied function', no need to pass "_", does not require 2nd parameter passed
   println("1.2: " + f(5) )

}

  println()

{ // 2. Currying - it does not return a function - it returns an Int result.
  // It expects 2nd parameter to be passed,
  // but if that is not provided - it returns a function that expect this parameter
  def add2(a:Int)(b:Int) = {a + b}

  // 2.1
  println ("2.1: " + add2(5)(6))

  //val f =  add(5) // does not compile, expecting 2nd param passed
  val f =  add2(5)_  // can treat it as 'partially applied function' because 2nd parameter is defined,
                        // so we may mark it to be omitted
  println("2.2: " + f(5) )

}

}
