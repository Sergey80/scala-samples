package scala_99

import scala.annotation.tailrec

//# duplicates #list #consecutive #tailrec #dropWhile

object t8_duplicate_list extends App {

  // 1. use accumulator & tailrec

  @tailrec
  def compress[T](list:List[T], previous:T = "", acc:List[T] = List() ): List[T] = list match {
    case Nil                                => acc reverse
    case head :: tail if (head == previous) => compress(tail, head, acc) // not adding to accumulator if equal
    case head :: tail                       => compress(tail, head, head :: acc) // or: acc ++ List(head), then no need to use "reverse'
  }


  val result = compress(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))

  println (result) // List('a, 'b, 'c, 'a, 'd, 'e)


  // 2. make use of existing method: #dropWhile. recursion

  {
    def compress[T](list:List[T]): List[T] = list match {
      case Nil => Nil
      case head :: tail => head :: compress(tail.dropWhile(_ == head)) // x=> x == head
    }

    val result = compress(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))
    println (result)
  }


}
