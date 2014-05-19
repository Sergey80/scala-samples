package collections.lists

// #fold #list
// related: #reduce (see: ReduceExample) #template-method-pattern #strategy-pattern #decorator-pattern

// In nutshell: Fold is sophisticated version of 'reduce'

object FoldExample extends App {

  println("#1")

  // # 1 - simple
  {

    val result1 = List(1,2,3).foldLeft(0)(_ + _)

    // empty list will work, because initial value is set to 0 ('reduceLeft' would not work [#reduce related] )

    val result2 = List[Int]().foldLeft(0)(_ + _)

    println (result1) // 6
    println (result2) // 0

  }

  // traversing the list by foldRight
  {
    val list = List(1,2,3)
      list.foldRight(List[Int]()) { (right, result) =>
        right :: result
      }
    println ("traversing by foldRight: " + list)
  }

  // traversing the list by foldLeft
  {
    val list = List(1,2,3)
    list.foldLeft(List[Int]()) { (result, left) =>
      left :: result
    }
    println ("traversing by foldLeft: " + list)
  }

  // #2  similar to:  #template-method-pattern #strategy-pattern #decorator-pattern
  println("#2")

  // there are two operations ..
  def upperCaseOP (str:String) : String = {
    println(s"upperCaseOp($str)")
    str.toUpperCase
  }

  def addBarOP (str:String) : String = {
    println(s"addBarOP($str)")
    str + "bar"
  }

  def applyTransformations(initial: String, ops: Seq[String => String]) : String =
    ops.foldLeft(initial) {
      (currentResult, op) => op(currentResult) // applying an operation for each pair.
                                                // as you can see the pair has different types:
                                                    // 1. currentResult: String
                                                    // 2. op: String => String
    }

  // sequence of operations to be applied is important fo us. Like it is in #decorator-pattern

  val result = applyTransformations("hello", Seq( upperCaseOP, addBarOP))

  println ("result: " + result) // HELLObar

}

/*
Full output is:

 #1
 6
 0
 #2
 upperCaseOp(hello)
 addBarOP(HELLO)
 result: HELLObar

*/
