package scala_99


object t7_flatten_list extends App {

  def flatten2(xs: List[Any]): List[Any] = xs match {
    case Nil => Nil
    case (head: List[_]) :: tail => flatten2(head) ++ flatten2(tail)
    case head :: tail => head :: flatten2(tail)
  }

  println( flatten2(List(List(0, List(1,2)), 3, List(4, List(5, 6)))) )
  println ( flatten2 ( List(List(List(1))) ) )


}
