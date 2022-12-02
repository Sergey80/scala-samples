// Dynamic Programming principles examples

// Dynamic programming is when you use past knowledge to make solving a future problem easier.

// Dynamic programming is a useful type of algorithm
//  that can be used to optimize hard problems by breaking them up into smaller subproblems.

// By storing and re-using partial solutions (#memoization),
//  it manages to avoid the pitfalls of using a greedy algorithm.
// There are two kinds of dynamic programming, "bottom-up" and "top-down".

// "Top-down" is better known as "memoization" - is the idea of storing past calculations in order to avoid re-calculating them each time.

// DP = Recursion + Memoization

import scala.collection._

import java.util.concurrent.ConcurrentHashMap
import scala.jdk.CollectionConverters._

// #dynamic-programming
  // #top-down, #memoization
    // #greedy-algorithm #storing #re-using-partial-solutions
    // #automatic-memoization

object Fib extends App {

// #1

  // not "top-down" implementation. no #memoization is used
  def fib1(n:Int) : Int = {
    print (" " + n + " ")
    if (n <= 1) n
    else fib1(n-1) + fib1(n-2) // fib(..) will be recalculating each time.. That's bad.
  }

  println ( " ;result: " + fib1(5) ) //  5  4  3  2  1  0  1  2  1  0  3  2  1  0  1  ;result: 5

// #2

  val m = mutable.Map[Int, Int](0 -> 0, 1->1) // global mutable state - not good. but it shows the idea

  // using #memoization
  def fib2(n:Int) : Int = {

      print (" " + n + " ")

      if (m.get(n) == None) {
        // introducing #memoizattion
        m += (n -> ( fib2(n-1) + fib2(n-2) ) )  // reusing previously stored values
      }
      m.get(n).get
  }

  println ( " ;result: " + fib2(5) )


// #3

  // A bit improved version. Scala style. #automatic-memoization. Hiding mutable scope into the function.

  //#automatic-memoization


  // Class/trait level val compiles to a combination of a method and a private variable.
  // Hence a recursive definition is allowed.

  case class Memo[A,B](f: A => B) extends (A => B) {
    private val cache:concurrent.Map[A, B] = new ConcurrentHashMap[A, B]().asScala; // mutable state!
    def apply(x: A) = { // store: arg -> function result
      print (" " + x + " ")
      cache getOrElseUpdate (x, f(x))
    }
  }

  //Local vals on the other hand are just regular variables,
    // and thus recursive definition is not allowed.

  private val mFib : Memo[Int, Int] = Memo { // it is not a function, it is an object. Well it is an object-function.
    case 0 => 0
    case 1 => 1
    case n => {
      mFib(n-1) + mFib(n-2)  // each call's will be stored into the 'cache'
    }
  }

  def fib3(n:Int) = {
    mFib(n)
  }

  println ( " ;result: " + fib3(5) )

}

//Output will be like this:
//5  4  3  2  1  0  1  2  1  0  3  2  1  0  1  ;result: 5
//5  4  3  2  1  0  1  2  3  ;result: 5
//5  4  3  2  1  0  1  2  3  ;result: 5