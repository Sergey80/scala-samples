// #list-comprehension #haskell

package looping

/*
   While in Haskell:

   contact :: [[a]] -> [a]
   contact xss = [x | xs <- xss, x <- xs]
   result = contact [[1],[3,4,5],[6]]       -- [1,2,3,4,5]

*/

object ListComprehension extends App {

  def contact(xss: List[List[Int]]) : List[Int] = for ( xs <- xss ; x <- xs ) yield x
  val result = contact( List( List(1), List(3,4,5), List(6)) )

  println("result: " + result)      // result: List(1, 3, 4, 5, 6)

}
