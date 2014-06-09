package scala_99

// #tail-recursion #accumulating #if-else-expression #case #list-concatenation

object t9_pack_duplicates extends App {

  def pack[A] (list:List[A]):  List[List[A]] = {

    def loop( list:List[A], prior: A = list.head, acc1:List[A] = List(), acc2:List[List[A]] = List() ) : List[List[A]] = list match  {

        case Nil => (prior :: acc1) :: acc2

        case head::tail => loop(
                            list  =  list.tail,
                            prior =  head,
                            acc1  =  if (head == prior) head :: acc1 else List[A](head),
                            acc2  =  if (head != prior) acc1 :: acc2 else acc2
                          )
    }

    loop(list)

  }

  val result = pack(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)).reverse

  println(result) // List(List('a, 'a, 'a, 'a), List('b), List('c, 'c), List('a, 'a), List('d), List('e, 'e, 'e, 'e, 'e))

}
