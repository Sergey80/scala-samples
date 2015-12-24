// #list-comprehension #haskell #scala #for

package collections.lists

// List comprehension - Haskell comparing to Scala
object ListComprehension extends App{

  // in haskell:
  // variants = [ (x,y) | x <- [1,2,3], y <- ['a', 'b'] ]

  val variants = for (x <- List(1,2,3); y <- List("a", "b", "c")) yield (x,y)

  println(variants)
}
