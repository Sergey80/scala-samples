package scala_99

// #tail-recursion #accumulating #if-else-expression #case #list-concatenation

object t9_pack_duplicates extends App {
  def pack[A] (list:List[A]):  List[List[A]] = {

    def loop( list:List[A], prior: A = list.head, packAcc:List[A] = List(), resultAcc:List[List[A]] = List() ) : List[List[A]] = list match  {

        case Nil => (prior :: packAcc) :: resultAcc

        case head :: tail => loop(
                            list       =  list.tail,
                            prior      =  head,
                            packAcc    =  if (head == prior) head :: packAcc else List[A](head),
                            resultAcc  =  if (head != prior) packAcc :: resultAcc else resultAcc
                          )
    }

    loop(list)

  }

  val result = pack(List[Symbol](Symbol("a"), Symbol("a"), Symbol("a"), Symbol("a"), Symbol("b"), Symbol("c"), Symbol("c"), Symbol("a"), Symbol("a"), Symbol("d"), Symbol("e"), Symbol("e"), Symbol("e"), Symbol("e"))).reverse

  println(result) // List(List(Symbol("a"), Symbol("a"), Symbol("a"), Symbol("a")), List(Symbol("b")), List(Symbol("c"), Symbol("c")), List(Symbol("a"), Symbol("a")), List(Symbol("d")), List(Symbol("e"), Symbol("e"), Symbol("e"), Symbol("e")))

}
