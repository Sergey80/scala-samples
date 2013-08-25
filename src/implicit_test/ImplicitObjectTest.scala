package implicit_test


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
