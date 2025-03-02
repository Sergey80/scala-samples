import cats._
import cats.implicits._

import cats.Monoid
import cats.implicits._

@main
  def main(): Unit = {

  val result = "Hello " |+| "Cats!" // Here, |+| is Cats' syntax for combining values.
  println(result) // "Hello Cats!"

  // why bother?
  /**
   * In Scala, + works only for types that have built-in support for addition, such as:
   *
   * Int, Double, etc.
   * String (concatenation)
   * List (concatenation of elements)
   */

  // But what if you want a generic way to combine values without knowing their
  // type upfront? This is where |+| and Semigroup (from Cats) become useful.
}

