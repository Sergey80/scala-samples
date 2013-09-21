package returns

/*
 * This code is next step of ReturnSampleBad. Please see ReturnSampleBad first !
 * #return
 * related #blocks
 */
object ReturnSampleGood extends App {

  // can you answer what this function returns - 0 or 1 ?

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

  val result = foo( List(1,2,3) )

  println ( result )  // ?


}

