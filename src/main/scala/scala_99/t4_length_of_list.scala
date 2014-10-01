package scala_99

// #list #tail #tail #recursive
object t4_length_of_list extends App {

  // 1. looping / tail solution.

  def length(list:List[Int]):Int = {
    def loopToNil(list:List[Int], count:Int):Int = list match { // we have to keep 'count' between calls
      case Nil => count
      case _ => loopToNil(list.tail, count+1)
    }
    loopToNil(list, 0)
  }

  println ( length(List(1, 1, 2, 3, 5, 8)) ) //6

  // 2. recursive solution.

  def length2(list:List[Int]):Int = list match {
    case Nil => 0
    case _ :: tail => 1 + length2(list.tail) // 1 + 1 + ... + 0
  }

  println ( length2( List(1,2,3,4,5,6) ) ) // 6

}
