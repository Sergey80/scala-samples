package higher_order_functions

// Higher Order Function example.

object HigherOrderFunctionTest extends App {

  // #1
  // taking a function without trying to pass any param to it
  def takeFunction(f: (Int=>Int) ) = {
     println(f)       // print: <function1>, - not evaluated yet
     println( f(1) )  // print: 1 - evaluation take place here !
  }

  // #2
  // taking a function with one single param 'p'
  def takeFunction2(f: (Int=>Int), p:Int ) = {
    println(f(p))   // evaluation takes place here !
  }

  // #3  composition of two (Int=>Int)
  def takeTwoFunctions1(f1: (Int=>Int), f2:(Int=>Int)) = {
     // empty
  }

  def takeTwoFunctions2(f1: (Int=>Int), f2:(Int=>Int), p1:Int, p2:Int) = {
    f1(p1)
    f2(p2)
  }


  def f1(p1:Int) = {println ("f1 is invoking"); p1}
  def f2(p1:Int) = {println ("f2 is invoking"); p1}

  // takeFunction( f(x) ) // we can not do it. Worth to remember the syntax !

  // 1--

  println ("#1 try:")

  takeFunction( f1 ) // passing a reference to function without passing any parameter to it.
                         // So then a function "f1" is not evaluating in the moment of passing
                         // "f1 is invoking" only once

  // 2--

  println ("\n#2 try:")

  takeFunction2( f1, 1)  // this is how "Higher order function" is able to pass an parameter to a function it takes as an argument
                              // and by passing it - an evaluating is taking place

  // 3-- composition of two (Int=>Int)

  println ("\n#3 try:")

  takeTwoFunctions1(f1, f2) // nothing will be printed, because f1, f2 are just references, so functions are not evaluating
                             // evaluation takes place on the moment of passing param to the function

  takeTwoFunctions2(f1, f2, p1=1, p2=2)   // actually nothing special - that works like expected

/* Output:
  #1 try
  <function1>
  f1 is invoking
  1

  #2 try:
  f1 is invoking
  1

  #3 try:
  f1 is invoking
  f2 is invoking
*/

}
