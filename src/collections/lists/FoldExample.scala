package collections.lists

// #fold
// related: #reduce (see: ReduceExample)

// In nutshell: Fold is sophisticated version of 'reduce'

object FoldExample extends App {

  // # 1 - simple
  {

    val result1 = List(1,2,3).foldLeft(0)(_ + _)

    // empty list will work, because initial value is set to 0

    val result2 = List[Int]().foldLeft(0)(_ + _)

    println (result1) // 1
    println (result2) // 0

  }

  // #2

  // there are two operations ..
  def upperCaseOP (str:String) : String = str.toUpperCase
  def addBarOP (str:String) : String = str + "bar"

  def applyTransformations(initial: String, ops: Seq[String => String]) =
    ops.foldLeft(initial) {
      (cur, op) => op(cur)
    }

  val result = applyTransformations("hello", Seq( upperCaseOP, addBarOP))

  println (result)

}
