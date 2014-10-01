// #list-comprehension #haskell
// http://en.wikipedia.org/wiki/List_comprehension

package looping

/*
   While in Haskell:

   contact :: [[a]] -> [a]
   contact xss = [x | xs <- xss, x <- xs]           -- 'hungarian-notation' for lists. meaning xss - list of lists; xs - list of x; x - end value
   result = contact [[1],[3,4,5],[6]]               -- [1,2,3,4,5]

*/

object ListComprehension extends App {

  def contact(xss: List[List[Int]]) : List[Int] = for ( xs <- xss ; x <- xs ) yield x
  val result = contact( List( List(1), List(3,4,5), List(6)) )

  println("result: " + result)      // result: List(1, 3, 4, 5, 6)

}

// As you can seen Haskell code is more readable: Two reasons: 1. 'hungarian-notation' is used in Haskell 2. square-brackets for list
// About Scala vs Haskell list syntax: http://stackoverflow.com/questions/6171955/scala-alternative-list-syntax-with-square-brackets-if-possible

