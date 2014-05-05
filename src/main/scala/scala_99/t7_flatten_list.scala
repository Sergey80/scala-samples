package scala_99


object t7_flatten_list extends App {

  def flatten[T](list: List[T]): List[T] = list match {
    case Nil => Nil
    case head :: tail => (head match {
      case l: List[T] => flatten(l)
      case i => List(i)
    }) ::: flatten(tail)
  }

  println( flatten(List(List(0, List(1,2)), 3, List(4, List(5, 6)))) )


}
