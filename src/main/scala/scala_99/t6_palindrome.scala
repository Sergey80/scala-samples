package scala_99

// #palindrome #last #reverse #sameElements

object t6_palindrome extends App {

  def isPalindrome_v1[A](list: List[A]): Boolean = list match  {  // v1
    case List() => true
    case List(_) => true
    case list if (list.head != list.last) => false              // that's not very efficient.
    case list => isPalindrome_v1(list.drop(1).reverse.drop(1))  //  Will improve that in the next version
  }

  def isPalindrome_v2[A](list: List[A]): Boolean = list match {

    case List(_) => true

    case list => {

      val middleLeft = list.length/2
      val middleRight = if (list.length % 2 != 0) list.length/2 + 1 else list.length/2

      val left = list.slice(0, middleLeft)                     // do slice only once
      val right = list.slice(middleRight, list.length).reverse // do reverse only once

      left sameElements (right)
    }

  }

  println ("--- v1 ---")
  printing( isPalindrome_v1 )

  println ("--- v2 ---")
  printing( isPalindrome_v2 )

  // --
  def printing(f: List[Int] => Boolean) = {
    println( f(List(1))) // true
    println( f( List(1,2,1)) )  // true
    println( f( List(1,2,3)) )  // false
    println( f( List(1,1,1)) )  // true
    println( f( List(2,1,1,2)) ) // true
  }


}
