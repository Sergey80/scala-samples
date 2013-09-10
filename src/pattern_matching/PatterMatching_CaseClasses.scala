package pattern_matching

/*
 * #pattern-matching #case-classes
 * related: #unapply-method #extractor #companion-object
*/
object PatterMatching_CaseClasses extends App {

  // 1.
  case class A(a:Int, b:Int)

  val a = A(1,2)

  a match {
    case A(_,_) => println("_,_") // this is possible because case classes have built-in "unapply" defined
  }


  // 2. To understand how pattern-matching works..
  // Let's define our own unapply method for general class. Kind of reinventing what case-classe provide for us by default

  object MyA { // #companion-object related

    def apply(a:Int, b:Int) = new MyA(a, b)

    def unapply(myA:MyA) : Option[(Int, Int)] = {  // here it is. it will be invoked every time when 'case' invoking against it
      // the body of unapply tells if the argument has matched or not
      Some(myA.a, myA.b) // in our case it match all the time
    }
  }
  class MyA(val a:Int, val b:Int) // was born to be able to participate in pattern-matching

  val myA = MyA(1, 2) // same as C.apply(1)

  myA match {
    case MyA(1, 2) => println ("got (1,2)") // 'case C(1)' will lead to C.unapply(c) invocation. Make sense to remember this !
  }

  // (that says that if a class does not have unapply method, it can not be used in pattern-matching)

  // let's check whether we can still use pattern magic like "_" having our general class
  myA match {
    case MyA(_, _) => println ("got it (_,_)") // all "_"-magic still here even for general(not case) classes
                                                  // provided that they have unapply method defined
  }

}
