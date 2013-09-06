package partially_applied_functions

/*
 #partially-applied-function
 related: #currying
*/

object PartiallyAppliedFunction extends App {

  // #1
  def f1(a:Int, b:Int) = a + b  // was not born to be partial (there is no any "_" in its definition)

   //def partFun = f1(2)_  // can not do it..  (not so easy.. eh? )

  // #1.1
  // but we can reuse this - to turn it into partially applied function:
  val x = f1(1, _:Int)  // first argument is defined, but since we use "_" then 'x' is partially applied function with ine argument
  println("x: " + x ) // <function1>
  println("x(2): " + x(2))

  // #2 "currying"
  def f2(a:Int)(b:Int) = a + b  // this is "currying". we can use partially applied function with it (#currying related)

  def partFun = f2(2)_

  println("partFun: " + partFun)
  println("partFun: " + partFun(2))

  // this makes us think that having "currying"-function is easier to make that function partial than do the same having for normal function.

 /*
 Output:
  x: <function1>
  x(2): 3
  partFun: <function1>
  partFun: 4
 */
}
