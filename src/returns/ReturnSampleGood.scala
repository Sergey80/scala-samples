package returns

/*
 * This code is next step of ReturnSampleBad. Please see ReturnSampleBad first !
 * #return
 * related #blocks
 */
object ReturnSampleGood extends App {

  // now this method will work as expected

  def foo(list:List[Int], count:Int = 0): Int = {

    if (list.isEmpty) { // #block related

     1                  // same as "return 1" - return in Scala does not return out of the function, but out of the block !

    }

      else             // use "else" if you do not want you loos you "1" as a return value

    {
      foo(list.tail, count + 1)
    }

    // count          // we do not need this, otherwise it will win(override all previous returns) and return 'count' which is "0'

  }

  //just more consist version of the function:

  def fooShort(list:List[Int], count:Int = 0) :Int = list match {
    case Nil => 1  // Nil = empty list
    case List(_*) => fooShort(list.tail, count + 1)
  }

  val result1 = foo( List(1,2,3) )

  val result2 = fooShort( List(1,2,3) )

  println ( result1 )  // 1
  println ( result2 )  // 1


}

