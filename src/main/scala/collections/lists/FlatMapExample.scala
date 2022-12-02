package collections.lists

// #flatmap (#flatmap-method)
// related: #map-method #flatten #product-trait (#product)

object FlatMapExample extends App {

  // trying to be simple.

  val fruits = List("apple", "banana", "orange")  // here it is: sequence of strings. Or: sequence of sequences of chars

  // map

  // # 1
  {
    val bigFruits = fruits map (_.toUpperCase)

    println (bigFruits) // List(APPLE, BANANA, ORANGE)
  }

  // #2 flatten
  {

    val result = fruits.flatten // flatten - converts each item (String [sequence of chars]) to initial collection -
                                  // to List of Chars

    println ("fruits flatten: " + result) // List(a, p, p, l, e, b, a, n, a, n, a, o, r, a, n, g, e)

  }

  // flatMap = flatten + map

  // #3
  {
    val bigLetters = fruits flatMap(_.toUpperCase)

    println ("bigLetters:" + bigLetters) // List(A, P, P, L, E, B, A, N, A, N, A, O, R, A, N, G, E)

    // Note: bigLetters is: List[Char]  ! Sequence of chars were converted to initial collections to List

    // so, this is sam as:

    val bigLetters2 = fruits.flatten map(_.toUpper) // 'toUpper' here is method of Char (not 'toUpperCase' now)

    println ("bigLetters2: " + bigLetters2)


  }

  // so flatMap is flatten + map


  // #4 closer to reality. some often uses case..

  // 2dArray - for vs flatMap

  {

    val matrix = Array.ofDim[Int](2,2)
    matrix(0)(0) = 1; matrix(0)(1) = 2
    matrix(1)(0) = 3; matrix(1)(1) = 4    // Array[Array[Int]] = Array(Array(1, 2), Array(3, 4))

    // for:
    {
      //matrix: Array[Array[Int]] = Array(Array(1, 2), Array(3, 4))

      val elements = for (   // elements: Array[Int] = Array(1, 2, 3, 4)
        row <- matrix;       // row is Array
        elem <- row
      ) yield elem

      println ("elements1: ")
      elements foreach ( print (_) ) // 1234

    }

    // flatMap:
    {
      val elements:Array[Int] = matrix flatMap( row => for (elements <-row) yield elements )

      println ("\nelements2: ")
      elements foreach ( print (_) ) // 1234
    }

    // so, you decide what is better for you - to use 'for' or 'flatMap'. But at least, now you know what flatMap is

  }

  // #5 and of course example with Option(s)

  val results = List(None, Some(1), Some(2), None) // List[Option[Int]]

  val flatResult1 = results flatMap(x=>x)           //  List[Int] = List(1, 2)  .
  // So it treats Option as possible Collection of: Some or None

  // Related to Product trait:
  // http://stackoverflow.com/questions/1301907/how-should-i-think-about-scalas-product-classes
  // http://en.wiktionary.org/wiki/Cartesian_product

  // same as (int this case)

  val flatResult2 = results.flatten                 //  List[Int] = List(1, 2)


}
