package scala_99


object t7_flatten_list extends App {


  {  // pure recursion

    println("pure recursion: ")

    def flatten(xs: List[Any]): List[Any] = xs match {
      case Nil => Nil
      case (head_list: List[Any]) :: tail => flatten(head_list) ::: flatten(tail)
      case head_elem :: tail => head_elem :: flatten(tail)    // looping through the list
    }

    val list = List(1, List(2, 3), 4)
    println(list + " -> " + flatten(list))

  }

  { // recursion + accumulation
    println("recursion + accumulation: ")

    def flatten(xs: List[Any], acc:List[Any] = List()): List[Any] = xs match {
      case Nil => Nil
      case (head_list: List[Any]) :: tail   =>  flatten(head_list) ::: acc   // adding result to the list
      case head_elem :: tail                =>  head_elem :: flatten(tail) // looping through the list
    }

    val list = List(1, List(2, 3), 4)
    println(list + " -> " + flatten(list))


  }

  {
    println("with flatMap: ")

    def flatten(xs: List[Any]) : List[Any] = xs flatMap {
      case list:List[Any]  => list
      case elem       => List[Any](elem)
    }

    val list = List(1, List(2, 3), 4)
    println(list + " -> " + flatten(list))

  }

  // if use standard flatten method then:
  {
    println("trying to use build-in flatten() method:")

    val list = List(1, List(2, 3), 4)
    // No implicit view available from Any => scala.collection.GenTraversableOnce[B].
    // println (  list flatten )  //

    // But this (one type-list) will work out
    val list2 = List(List(1), List(2, 3), List(4) )
    println ( list2.flatten )


  }


}
