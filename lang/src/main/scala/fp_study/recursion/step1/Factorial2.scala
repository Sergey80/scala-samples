package fp_study.recursion.step1

import scala.annotation.tailrec

// 2. Understanding recursion
// "Tail Recursion"

object Factorial2 extends App{

  // the problem with Factorial2 was that we have to keep function calls in the Stack

  // i.e. this "i * factorial(i-1)" leads to keeping values

  // another words: If we did not have "i * ... ", we may avoid using Stack at all !

  // let's try
  def factorial(i: Int, result:Int = 1) : BigInt = {  // will stick to same algorithm.
    if (i == 0) result
    else factorial(i-1, result = result * i)
  }

  println( "factorial(5) :" + factorial(5) )

  // As you can see what we do: we just accumulating Result and storing it between function calls - just passing it as an argument.

  // So no need to add anything to "head" of recursion call.

  // We just leave recursion call to be LAST expression - this mean "tail recursion"

  // Let's try if it fails with big number as it was before:

  println ( "factorial(9999): " + factorial(9999) ) // No, there is no StackOverflow exception, That's good!
                              // But we got "0", because BigInt is out of range for our big result.
                              // This is not our problem for now.

  // Important: Scala supports special annotation called "@tailrec"
  // you may put it over the function to make sure you use TAIL recursion (it will fail if not)

  // So our function may look like this:

  @tailrec
  def factorial2(i: Int, result:Int = 1) : BigInt = {
    if (i == 0) result
    else factorial2(i-1, result = result * i)
  }

  println ( "factorial2(5): " + factorial2(5) )


  // You may wonder. "Is tail recursion still "recursion" if there is no any stack-helping?"
  //
  // The answer might be:
  // For you it "looks like" as recursion (because 'function calls itself'),
  // but compiler is able to rewrite/optimize this under the hood  by using loops (like: "while" or "for")

  // Just do not forget (from another side): You may use recursion calls to implement looping without side-effects (vars).

  // But if you would use "while" and "for" by your will, it might look not so nice and cool (as with recursion), let's check:

  def factorial3(i:Int) : BigInt = {

    var current = i   // VAR is not cool at all (you know !)
    var result  = 1

    while(current != 0) {
      result = result * current
      current = current - 1
    }

    result

  }

  println( "factorial3(5): " + factorial3(5) )

  // So, the last example in not recursion at all. And you can see that this code smells: we had to introduce to VARS



}
