package scala_99

// Find the last but one element of a list.

// #Nil #pattern-matching #list
// #pre-last-element
object t2_pre_last_in_list extends App {

  def preLast(list: List[Int]): Int = list match {

    case elem :: tail if (tail.size == 1) => elem  // 'if' after 'case' is called 'guard'

    case elem :: tail => preLast(tail)

  }

  val result = preLast( List(1,2,3,4,5) )

  print( result )

}
