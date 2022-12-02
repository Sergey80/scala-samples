package implicits

/*
  #implicit
  related: #implicit-class #implicit-parameters
 */

/*
 * Add new method to exiting Int class, That works in particular scope.
*/

object ImplicitsScope {

  // defines implicits method for Int class
  implicit class ExtendedInt(x: Int) {  // the method of class is not really matter, it is jus a scope

    def times[A]( f: => A ): Unit = {    // new method 'times()' will be added to Int class
      for(i <- 1 to x) {
        f
      }
    }

  }

}


object Starter extends App {

  foo1

  def foo1 = {
    import implicits.ImplicitsScope._
    5 times println("hello")  // you see ! there is a new method times() added in Int class (for this context only!)
  }

  // this will not work, because scope where method "times()" was defined is not imported
  /*
  def foo2 = {
    5 times println("this should not work")
  }*/

}
