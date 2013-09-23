package recursion.puzzle

/*
 *
 * #recursion #return #puzzle
 * related
 *
 * This application shows return works in Scala.
 */
object ReturnSampleBad extends App {

  // can you answer what this function returns - 0 or 1 ?

  def foo(list:List[Int], count:Int = 0): Int = {

    if (list.isEmpty) {
      return 1
    }

    foo(list.tail, count + 1)

    count
  }

  val result = foo( List(1,2,3) )

  println ( result )  // ?


}
