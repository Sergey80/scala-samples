
import cats.Monoid
import cats.implicits._

// we know that we can use monoid from scalaz too.

@main
def monoid_1(): Unit = {
  val sum = Monoid[Int].combine(10, 20) // 30

  // Monoid: Semigroup (with combine) + Identity Element

  val zero = Monoid[Int].empty // 0

//  Why is Monoid useful ?
//
//  The identity element(empty) lets you initialize values correctly
//.
//  You can fold collections using monoidal operations:

  println(sum) // 30
  println(zero) // 0

  // ----------

  val list = List(1, 2, 3, 4, 5)

  // fold map calls foldLeft, and foldLeft calls combine() method on Monoid, and list is Monoidal construct

  val sum2 = list.foldMap(identity)(Monoid[Int]) // 15

  // Monoid[Int] already has predefined methods in Cats,
  // which is why we can use it without defining anything manually.

  println(sum2) // 15


  // --- wihout Cats

  val list1 = List(1, 2, 3, 4, 5)
  val sum1 = list.foldLeft(0)(_ + _) // could be simpler opt (but only works with numbers)
  println(sum1) // 15

  // going back to foldMap and Monoid:

  // It's not obvious from the name that foldMap calls combine!
  // This is one of the downsides of abstract algebra-based APIs:
  // they hide the actual implementation details behind abstractions like Monoid.
  // Let’s make it clear why foldMap uses combine.

  // even foldLeft looks rather strange

  // simple foreach would do too

  var sum3 = 0
  List(1, 2, 3, 4, 5).foreach(sum3 += _)
  println(sum3) // 15

  // we should ask ourelf this practical questions somethins. why we go abstract algebra.

  // For a simple AWS Lambda function, you don’t need Cats or even foldLeft:

  // Right—thinking in terms of monads,
  // functors, and type classes requires extra brain power.

  // If you already know how to solve a problem using simpler,
  // explicit methods, why add this abstraction overhead?
}
