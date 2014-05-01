package scala_99

// #list-reverse #reverse
// #list-concatenation #list-copy
object t5_reverse_list extends App {

  // --

  def reverse1(list:List[Int]) : List[Int] = {

    def copyList(from:List[Int], to:List[Int]): List[Int] = from match {
      case Nil  => to
      case elem :: tail => copyList(from.tail, elem :: to ) // add an element to the list
    }

    copyList( from = list, to = Nil ) // #list-copy

  }

  // --

  def reverse2(list:List[Int]) : List[Int] = {

    def copyList(from:List[Int], to:List[Int]): List[Int] = from match {
        case Nil  => to
        case elem :: tail => copyList(from.tail, List(elem) ::: to ) // #list-concatenation - tail recursion
    }

    copyList( from = list, to = Nil ) // #list-copy

  }

  // --
  // #list-concatenation - classic recursion (putting some 'prints' to see how it goes)

  def reverse3(list:List[Int]) : List[Int] = list match {
    case Nil => list // 1
    case h :: tail => reverse3(tail) ::: List(h) // 2
  }
//  2: List(1, 2, 3, 4, 5)
//  2: List(2, 3, 4, 5)
//  2: List(3, 4, 5)
//  2: List(4, 5)
//  2: List(5)
//  1: List()

//
  println( reverse1(List(1, 2, 3, 4, 5)) ) // List(5, 4, 3, 2 ,1)

  println( reverse2(List(1, 2, 3, 4, 5)) ) // List(5, 4, 3, 2 ,1)

  println( reverse3(List(1, 2, 3, 4, 5)) ) // List(5, 4, 3, 2 ,1)


}
