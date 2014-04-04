package scala_99

// Find the Kth element of a list.

// #list #loop #stable-identifier
object t3_k_elem_in_list extends App {

  def nth(k:Int, list:List[Int]): Int =  {

    def loopToK(list:List[Int], i:Int = 0): Int = i match {
      case `k` => list.head           // #stable-identifier
      case _ => loopToK(list.tail, i+1)
    }

    loopToK(list)

  }


  println ( nth(2, List(1, 1, 2, 3, 5, 8)) ) // 2

}
