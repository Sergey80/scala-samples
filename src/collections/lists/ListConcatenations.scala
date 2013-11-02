package lists

// #list #concatenation

object ListConcatenations extends App {

  val list1 = List(1,2,3)
  val list2 = List(4,5,6)

  val result1 = list1 ::: list2 // add to the beginning of list2. So ':::' is method of list2.
  val result11 = list2.:::(list1) // same as above

  val result2 = list1 ++ list2  // add to the end of list1. '++' is method of list1

  println ("list1 ::: list2 : " + result1)
  println ("list2.:::(list1) : " + result11)

  println ("list1 ++ list2 : " + result2)

  // what about "::" ?

  val result3 = list1 :: list2 // It add new ELEMENT to the BEGINNING of the list2. So, :: is method of list2

  println ("list1 :: list2 : " + result3) // List(List(1, 2, 3), 4, 5, 6)

}
