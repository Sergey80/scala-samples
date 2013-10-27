package fp_study.recursion.step2_listl

/**
 * There are simple examples of recursive algorithms which are related to List.
 *
 * Examples comes with step by step explanations to make it clear to understand applied recursion
 */

object ListAlgorithms extends App {

    // --- init -- (recursive)

    val list = List(1,2,3)

    // So, what we need is just skip very last element

    def init[T](xs: List[T]) : List[T] = xs match {
      case List() => throw new Error("empty list")
      case List(x) => List() // empty list - this makes it skip very last element of tail that is passing
      case head :: tail => head :: init(tail)             // 1.  1 :: tail(2, 3) - 1 :: (2, Nil)
                                                          // 2.  2 :: tail(3)  -> 2 :: (Nil)
                                                          // 3.  3 -> Nil
    }

    println ("init of [1,2,3]: " + init(list) )

    // -- concat --- (recursive)

    val list1 = List(1,2,3)
    val list2 = List(4, 5)

    def concat[T](list1: List[T], list2: List[T]) : List[T] = list1 match {
      case List() => list2
      case head :: tail => head :: concat(tail, list2)       // 1 :: concat( [2,3], 4,5)
                                                             // 2 :: concat( [3],   4,5)
                                                             // 3 :: -> [4,5] (list2)   --> 1,2,3,4,5

    }

    println ("concat: " + concat(list1,list2))

    // --- reverse --- (recursive)
    {
      val theList = List(1,2,3,4,5)

      // to come up with that algorithm as a pattern (that you will repeat recursively),
      // try to think on simplest case, as if you have list of two elements [1,2]
      // what should you dot then?
      // you should put last element to be first one, so basically:

      // pattern is: tail + header

      // where tail is decreasing recursively

      def reverse[T](list:List[T]): List[T] = list match {

        case List() => list
        case head :: tail => reverse(tail) ::: List(head)  // should "++" be deprecated then ???
                                                            // reverse (2,3,4,5) ::: = 5,4,3,2 + 1
                                                            // reverse (3,4,5) ::: 2 = 5,4,3 + 2
                                                            // reverse (4,5) ::: 3   = 5.4 + 3
                                                            // reverse (5) ::: 4     = 5 + 4
                                                            // reverse (empty) = 5

      }

      println ("reverse of [1,2,3,4,5]: " + reverse(theList) )

    }  // complexity is: n*n. Not nice !


}
