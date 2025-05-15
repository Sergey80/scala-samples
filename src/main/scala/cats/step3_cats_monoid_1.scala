
import cats.Monoid
import cats.implicits._

// Monoid - extends Semigroup, so it has `combine` defined
// But it also has `empty` (identity) -it defines what it means to be empty
// So, Monoid is something that combines and know how it is to be empty.

@main
def monoid_1(): Unit = {
  val sum = Monoid[Int].combine(10, 20) // 30

  // Monoid: Semigroup (with combine) + Identity Element

  val zero = Monoid[Int].empty // 0

// With that, for example
//  You can fold collections using monoidal operations:

  println(sum) // 30 (uses combine)
  println(zero) // 0

  // ----------

  val list = List(1, 2, 3, 4, 5)

  // fold map calls foldLeft, and foldLeft calls combine() method on Monoid, and list is Monoidal construct

  val sum2 = list.foldMap(identity)(Monoid[Int]) // 15 (identity is defined elsewhere),
  // here we're passing Monoid of Int - that knows how to combine Integers and what it meeans to be empty once found itself in boxed structure as a list - when many of its type is there,
  // if 0 - it means nobody is here, empty.

  // Monoid[Int] already has predefined methods in Cats,
  // which is why we can use it _without_ defining anything manually.

  println(sum2) // 15


  // --- Life Without Cats:

  val list1 = List(1, 2, 3, 4, 5)
  val sum1 = list.foldLeft(0)(_ + _) // could be simpler opt (but only works with numbers - because + is defined for numbers only, its algebra works with numbers)
  // see, there we did not pass Monoid - that is why we have to be specific - using + explicitly (no combine is called, just function that plays with operator +)
  println(sum1) // 15 - it explict, we can read it, sure + for all of those in list will give the sum

  // while with  list.foldMap - it would ask for Monoid

  // going back to foldMap and Monoid:

  // It's not obvious from the name that foldMap (list.foldMap(identity)(Monoid[Int]) calls combine!
  // This is one of the downsides of abstract algebra-based APIs:
  // they hide the actual implementation details behind abstractions like Monoid.
  // Let’s make it clear why foldMap uses combine.
  // so, when you see Monoid - you know it is about combine with the empty

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
  // explicit methods, why add this abstraction overhead. Think about it carefully.
}
