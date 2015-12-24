package scala_99

// Find the last element of a list.

// #Nil #pattern-matching #list
// #last-element #pattern-matching
object t1_last_in_list extends App {

  def last(list: List[Int]) : Int = list match {
    case elem :: Nil => elem            // Nil - means end of the list, empty list
    case elem :: tail => last(tail)
  }

  println( last( List(1,2,3) )  )  // 3
  println( last( List(1) )  )      // 1
//  println (last(List()) )        // not a case of now


}
