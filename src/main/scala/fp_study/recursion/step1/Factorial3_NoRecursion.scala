package fp_study.recursion.step1

// One-line-function implementation

// Inspired by this Haskell article: http://www.willamette.edu/~fruehr/haskell/evolution.html

// #stream #infinit-list #factorial #algorithm

object Factorial3_NoRecursion extends App {

  def fac(n:Int) = Stream.range[Int](1,n+1).foldLeft(1) (_ * _)

  val result = fac(5)

  println (result) // 120

}
