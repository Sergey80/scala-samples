package cats

import cats.*
import cats.implicits.*

case class Score(points: Int)

// define semigroup -- it is all about that method `combine`tht we define here
implicit val scoreSemigroup: Semigroup[Score] = (a, b) => Score(a.points + b.points)

/*
 You do not see method combine here, but it is there:
 defined as:

 `def combine(x: A, y: A): A`

  here we implement that combine method with the `+` operator.

  Nothing fancy, just an interface with one method.
*/

@main
  def main(): Unit = {

  // Now we can use |+| - that is Cats' syntax for combining values.
  val total1 = Score(10) |+| Score(20)
  val total2 = Score(10) combine Score(20) // same
  println(total1) // Score(30)
  println(total2) // Score(30)

  /**
    A Semigroup in Cats is like injecting a method into a type,
   much like defining a common trait or interface in OOP,
   but without super class or interface.
  **/
}

/**
 Even without Cats, you can achieve the same effect using Scala’s implicit mechanics.
 Cats just provides a structured and standardized way to use these concepts.
 I mean the Cats is just using what Scala can do anyhow, it just casts goe with standard naming
 - from abstract algebra.
**/

