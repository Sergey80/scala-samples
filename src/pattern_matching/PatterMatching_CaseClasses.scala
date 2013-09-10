package pattern_matching

/*
 * #pattern-matching #case-classes
 * related: #unapply-method
*/
object PatterMatching_CaseClasses extends App {

  // 1.
  case class A(a:Int, b:Int)

  val a = A(1,2)

  a match {
    case A(_,_) => println("_,_") // this is possible because case classes have built-in "unapply" defined
  }

  // TODO: do it
  // 2. to demonstrate how 'unapply' method works in the pattern-matching context,let's define our own 'unapply' method
  object C {
    def apply(i:Int):Int = i

    def unapply(i:Int) : Option[Int] = {  // here it is. it will be invoked every time when 'case' invoking against it
      // the body of unapply tells if the argument has matched or not
      Some(i)
    }
  }
  class C(i:Int)

  val c = C(1) // same as C.apply(1).

  c match {
    case C(1) => println ("got it") // 'case C(1)' will lead to unapply() invocation
  }

}
