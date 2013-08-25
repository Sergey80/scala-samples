package implicit_test

/*
 * Comparing to Java:
 *
  * 1. Like: Polymorphic call..
 *   - but here we have Objects not Classes
 *   - in Java no object but just static methods which we can not override or extend
 *   - we may avoid passing implicit object as param to some method, it will be done automatically (implicitly)
 *
 * Use cases:
 *
 * 1. Implicit object is often used as a "Session".
 *    If Session is defined somewhere then no need specify it all over the code when we use it (like db-session, web-session, etc..).
 *
 * 2. Json/XML (de)serialization - when default rule/algorithm is already applied about how to do this for object,
 *    we may provide our own algorithm for some type of objects.
 *
 * 3. In general: When we need substitute some existing algorithm by our own implementation
 *
 *
 * Limitations:
 *
 *  - Implicit object may Not be defined as extended/top structure.  They must be defined inside of another trait/class/object.
*/


trait Worker[T] {

  def doIt(it:T)

}

object ImplicitObjectTest extends App {

  implicit object StringWorker extends Worker[String] {

    def doIt(it: String) = {
      println(it.charAt(0))
    }

  }

  implicit object IntWorker extends Worker[Int] {

    def doIt(it: Int) = {
      println(it.toString.charAt(0))
    }

  }

  object Boss {

    def makeWorkedDo[T](work:T) (implicit worker: Worker[T]) {
       worker.doIt(work)
    }

  }

  // MAIN

  override def main(args : Array[String]) = {

    Boss.makeWorkedDo(work = "take the first letter from this string")  // prints "t"

    Boss.makeWorkedDo(work = 123456789) // print "1", as we can see here we don't know which worker will do the work

    Boss.makeWorkedDo(work = new Object) // if there is no worker, nothing will happen

  }

}
