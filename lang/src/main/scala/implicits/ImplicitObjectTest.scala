package implicits

/*
 #implicit
 related: #implicit-objects
*/

trait Worker[T] {
  def doIt(it:T)
}

object ImplicitObjectTest extends App {

  implicit object StringWorker extends Worker[String] {

    def doIt(it: String) = println(it.charAt(0))

  }

  implicit object IntWorker extends Worker[Int] {

    def doIt(it: Int) = println(it.toString.charAt(0))

  }

  object Boss {

    def passWorkToDo[T](work:T) (implicit worker: Worker[T]) {
       worker.doIt(work)
    }

  }

  // MAIN - a boss are passing some work to do, without specifying a worker

    Boss.passWorkToDo(work = "take the first letter from this string")  // prints "t"

    Boss.passWorkToDo(work = 123456789) // print "1", as we can see here we don't know which worker will do the work

    //Boss.makeWorkedDo(work = new Object) // if there is no worker, will fail on compilation

  // So, this quite close to OO' polymorphism, but this a bit smarter because:
   // - we use the same method "passWorkToDo", no need to override anything
   // - we may omit params, tha make function call even shorter

}
