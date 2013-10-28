package lists

object ListConcatenations extends App {

  val list1 = List(1,2,3)
  val list2 = List(4,5,6)

  val result1 = list1 ::: list2 // add to the beginning of list2. So ':::' is method of list2.
  val result11 = list2.:::(list1) // same as above

  val result2 = list1 ++ list2  // add to the end of list1. '++' is method of list1

  println ("list1 ::: list2 : " + result1)
  println ("list2.:::(list1) : " + result11)

  println ("list1 ++ list2 : " + result2)

}
