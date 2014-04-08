package scala_99

//# reverse
object t5_reverse_list extends App {

  def reverse(list:List[Int]) : List[Any] = {

    def loop(list2:List[Any]): List[Any] = {
      list match {
        case Nil => list2
        case elem :: tail => loop(elem :: list2 :: Nil)
      }
    }

    loop(list)

  }

  println( reverse(List(1, 1, 2, 3, 5, 8)) )

}
