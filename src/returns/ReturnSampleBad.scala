package returns

/*
 * This is GOOD code.This is just a demo.
 * It is a puzzle that supposed to make reader remember how "return" works.
 * Please see ReturnSampleGood as an alternative fixed version of the same scenario.
 *
 * #return #puzzle
 * related #blocks
 *
 * This application shows return works in Scala.
 */
object ReturnSampleBad extends App {

  // can you answer what this function returns - 0 or 1 ?

  def foo(list:List[Int], count:Int = 0): Int = {

    if (list.isEmpty) { // #block related
      return 1
    }

    foo(list.tail, count + 1)

    count
  }

  val result = foo( List(1,2,3) )

  println ( result )  // ?

  // see explanations here: http://stackoverflow.com/questions/18934871/scala-return-does-not-return

  // What is recommended way to write this code? see: ReturnSample2

}
