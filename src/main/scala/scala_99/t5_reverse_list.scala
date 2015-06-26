package scala_99

import scala.annotation.tailrec

// #list-reverse #reverse
// #list-concatenation #list-copy
object t5_reverse_list extends App {

  // --

  def reverse1(list:List[Int]) : List[Int] = {

    @tailrec
    def copyList(from:List[Int], to:List[Int]): List[Int] = from match {    // helper function
      case Nil  => to
      case elem :: tail => copyList(from.tail, elem :: to ) // add an element to the list
    }

    copyList( from = list, to = Nil ) // #list-copy

  }

  println("reverse1")

  println( reverse1(List(1, 2, 3, 4, 5)) ) // List(5, 4, 3, 2 ,1)

  // same but with no embedded/helper function. use default param
  @tailrec
  def reverse1_same(
               src:  List[Int],
               dist: List[Int] = List[Int]()      // default param
               )  :   List[Int] = src match {

    case Nil => dist
    case head :: tail => reverse1_same(src.tail, head :: dist)

  }

  println("reverse1_same:")
  println( reverse1_same( List[Int]() ))
  println( reverse1_same( List[Int](1) ))
  println( reverse1_same( List[Int](1, 2, 3) ))

  // --

  def reverse2(list:List[Int]) : List[Int] = {

    @tailrec
    def copyList(from:List[Int], to:List[Int]): List[Int] = from match {
        case Nil  => to
        case elem :: tail => copyList(from.tail, List(elem) ::: to ) // #list-concatenation - tail recursion
    }

    copyList( from = list, to = Nil ) // #list-copy

  }

  println("reverse2:")
  println( reverse2(List(1, 2, 3, 4, 5)) ) // List(5, 4, 3, 2 ,1)

  // --
  // #list-concatenation - classic recursion (putting some 'prints' to see how it goes)

  def reverse_classic(list:List[Int]) : List[Int] = list match {
    case Nil => list // 1
    case h :: tail => reverse_classic(tail) ::: List(h) // 2
  }
//  2: List(1, 2, 3, 4, 5)
//  2: List(2, 3, 4, 5)
//  2: List(3, 4, 5)
//  2: List(4, 5)
//  2: List(5)
//  1: List()

  println("reverse_classic")

  println( reverse_classic(List(1, 2, 3, 4, 5)) ) // List(5, 4, 3, 2 ,1)




}
