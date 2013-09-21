package returns

/*
 * #return
 * related #blocks
 *
 * This application shows return works in Scala.
 */
object ReturnSample extends App {

  // can you answer what this function returns - 0 or 1 ?

  def foo(list:List[Int], count:Int = 0): Int = {

    if (list.isEmpty) {
      return 1
    }

    return foo(list.tail, count + 1)

    count
  }

  val result = foo( List(1,2,3) )

  println ( result )  // ?

  // see explanations here: http://stackoverflow.com/questions/18934871/scala-return-does-not-return

}
