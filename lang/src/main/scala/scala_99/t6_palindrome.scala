package scala_99

// #palindrome #last #reverse #sameElements

object t6_palindrome extends App {

  def isPalindrome_v1[A](list: List[A]): Boolean = list match  {  // v1
    case List() => true
    case List(_) => true
    case list if (list.head != list.last) => false              // that's not very efficient.
    case list => isPalindrome_v1(list.drop(1).reverse.drop(1))  //  Will improve that in the next version
  }

  def isPalindrome_v2[A](list: List[A]): Boolean = list match { // v2

    case List(_) => true

    case list => {

      val middleLeft = list.length/2
      val middleRight = if (list.length % 2 != 0) list.length/2 + 1 else list.length/2

      val left = list.slice(0, middleLeft)                     // do slice only once
      val right = list.slice(middleRight, list.length).reverse // do reverse only once

      left sameElements (right)  // just compare
    }

  }

  // more officiant version if expecting array-like input/string

  def isPalindrome_v3(line: String): Boolean = {    // assuming line is string / array of chars - so we have random access by index

    for (i <- 0 until line.length/2) {
      if (line.charAt(i) != line.charAt(line.length -1 - i)) return false
    }

    true

  }

  println ("--- v1 ---")
  printing( isPalindrome_v1 )

  println ("--- v2 ---")
  printing( isPalindrome_v2 )


  println ("--- v3 ---")
  println( isPalindrome_v3("aabaa") )

  // --
  def printing(f: List[Int] => Boolean) = {
    println( f(List(1))) // true
    println( f( List(1,2,1)) )  // true
    println( f( List(1,2,3)) )  // false
    println( f( List(1,1,1)) )  // true
    println( f( List(2,1,1,2)) ) // true
  }


}
