package implicit_test

/*
 * Add new method to exiting Int class, That works in particular scope.
*/

object ImplicitsScope {
                                        // only one (no more) arg argument allowed for 'implicit class'
  implicit class ExtendedInt(x: Int) {  // does not matter how we call this class. It just defines 'class of methods'
                                        // x: Int - type 'Int' it does matter - we are adding new methods to "Int"

    def times[A]( f: => A ): Unit = {    // method 'times()' will be available as as it was in 'Int' class
      for(i <- 1 to x) {
        f
      }
    }

  }

}


object ImplicitClassTest extends App {

  foo1

  def foo1 = {
    import implicit_test.ImplicitsScope._
    5 times println("hello")  // now we can use this new 'times()' method for Int class
  }

  // this will not work, because scope where method "times()" was defined is not imported
  /*
  def foo2 = {
    5 times println("this should not work")
  }*/

}
