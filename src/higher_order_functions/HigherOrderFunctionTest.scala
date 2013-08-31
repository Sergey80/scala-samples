package higher_order_functions

// Higher Order Function example.

object HigherOrderFunctionTest extends App {

  def takeFunction(f: (Int=>Int) ) = {
     println(f)       // print: <function1>, - not evaluated yet
     println( f(1) )  // print: 1 - evaluation take place here !
  }

  def takeFunction2(f: (Int=>Int), p:Int ) = {
    println(f(p))   // evaluation take place here !
  }


  def f1(x:Int) = {println ("f1 is invoking"); x}
  def f2(x:Int) = x

  // takeFunction( f(x) ) // we can not do it. Worth to remember the syntax !

  println ("try 1:")

takeFunction( f1 ) // passing a reference to function without passing any parameter to it.
                       // So then a function "f1" is not evaluating in the moment of passing
                       // "f1 is invoking" only once

  println ("\ntry 2:")

takeFunction2( f1, 1)  // this is how "Higher order function" is able to pass an parameter to a function it takes as an argument
                            // and by passing it - an evaluating is taking place

}
