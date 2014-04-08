package scala_99

// #list-reverse #reverse
// #list-concatenation #list-copy
object t5_reverse_list extends App {

  def reverse(list:List[Int]) : List[Int] = {

    def copyList(from:List[Int], to:List[Int]): List[Int] = from match {
        case Nil  => to
        case elem :: tail => copyList(from.tail, List(elem) ::: to ) // #list-concatenation
    }

    copyList( from = list, to = List[Int]() ) // #list-copy

  }

  println( reverse(List(1, 2, 3, 4, 5)) ) // List(5, 4, 3, 2 ,1)

}
