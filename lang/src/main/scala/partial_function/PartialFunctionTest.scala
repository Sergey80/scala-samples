package partial_function

/*
 * Partial function (math.) - it's opposed to "total" functions.   http://en.wikipedia.org/wiki/Partial_function
 *
 * So, Partial Function is a function where we could pass an argument for which
 * this function is not defined / where it does not have sense /  does not exist.
 *
 * Like: (x:Int, d:Int) => Int => x/d. When d = 0 is applied, then the value of the function does not exist
 *
 * #partial-function
 * related #pattern-matching
 *
 */
object PartialFunctionTest extends App {

  // 1, let's define a function that divides 10 by d
  def divide10By(d:Int):Int = { // this function does not exists when d = 0
    10/d
  }

  // test it (1) :

//  divide10By(0) // will fail - "ArithmeticException: / by zero"


  // 2, the option to define this function as "PartialFunction" to expect 0 argument that leads
  val divide2 = new PartialFunction[Int, Int] {  // first param represents argument, 2nd - returning value

    def apply(d: Int): Int = 10 / d   // apply has all our function's logic

    def isDefinedAt(d: Int): Boolean = d != 0

  }

  // test it (2) :

   println ( divide2.isDefinedAt(2) ) // "true"
   println ( divide2.isDefinedAt(0) ) // "false"

//  divide2(0)  // still fail (of course, because nobody uses 'isDefinedAt()' method )

 // 2.1 let's improve our 2nd try. To apply some case-magic.
    // Actually we could skip implementing PartialFunction class manually, but just make it done for you with help of case-anonymous function

  def divide3: PartialFunction[Int,Int] = {  // Note ! for now we are passing anonymous function with "case"
    // this make birth of anonymous function
    case d:Int if d!=0 => 10/d                // thus we let built-in apply(f:Int=>Int) method create PartialFunction impl
                                              // based on anonymous function we are passing
  }

  // what about "isDefinedAt" ?
  println ( divide3.isDefinedAt(2) )  // "true"  ! So, passing anonymous function as an argument,
  println ( divide3.isDefinedAt(0) )  // "false" ! makes "isDefinedAt()" function to be implemented, based on
                                                 // case-anonymous-function, including all if-guard


//  divide3(0)  // scala.MatchError: 0 (of class java.lang.Integer) This already different case-related error! That's good!

 // 2.2 Let' use case-advantage, to let use pass not only "Int", but any argument
 def divide4: PartialFunction[Any,Int] = {  // Note ! for now we are passing anonymous function with "case"
   // this make birth of anonymous function
   case d:Int if d!=0 => 10/d                // thus we let built-in apply(f:Int=>Int) method create PartialFunction impl
   // based on anonymous function we are passing
 }

 divide4.isDefinedAt("hello") // how we can test against String .. or Any other type  - it returns "false"
// divide4("hell0")   // no magic: scala.MatchError: hell0 (of class java.lang.String)  - case related error

 // So, what we need is a function that could accept Partial Function an argument before applying it,
 // first check isDefinedAt() method
 def acceptPartial(pf:PartialFunction[Any, Int], arg:Any) = { // returns: Any
   if (pf.isDefinedAt(arg)) pf(arg) // that's it
 }

  val result1 = acceptPartial( divide4, 2 )
  val result2 = acceptPartial( divide4, 0 )

  println("result: " + result1) // "10"
  println("result: " + result2) // "()" - Any


 // 3. Using Partial Function in Scala collection
 val list = List(1,2, "a", "b")

// list.map{ case x:Int => x+1 }  // map accepts anonymous (not Partial) Function,
                                // that's why it fails, because the "case" does not handle / expect String type

 //but if we  use a method that accept PartialFunction as argument, then ...
  val list2 = list.collect{case x:Int => x+1} // def collect[B, That](pf: PartialFunction[A, B])

  list2 foreach( print(_) )  // 23


  // ADVICE:
  // Just remember what "Partial" stands for in terms of math/function, and look carefully which
  // function expects Partial function and which does not.

}
