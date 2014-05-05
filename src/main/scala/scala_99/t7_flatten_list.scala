package scala_99


object t7_flatten_list extends App {

  def flatten(xs: List[Any]): List[Any] = xs match {
    case Nil => Nil
    case (head: List[_]) :: tail => flatten(head) ++ flatten(tail)
    case head :: tail => head :: flatten(tail)
  }

  println( flatten  (List(List(0, List(1,2)), 3, List(4, List(5, 6)))) )
  println ( flatten ( List(List(List(1))) ) )


}
