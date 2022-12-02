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

  val myA = MyA(1, 2) // same as MyA.apply(1,2)

  myA match {
    case MyA(1, 2) => println ("got (1,2)") // 'case MyA(1,2)' will lead to MyA.unapply(1,2) invocation. Make sense to remember this !
  }

  // (that says that if a class does not have unapply method, it can not be used in pattern-matching)

  // let's check whether we can still use pattern magic like "_" having our general class
  myA match {
    case MyA(_, _) => println ("got it (_,_)") // all "_"-magic still here even for general(not case) classes
                                                  // provided that they have unapply method defined
  }

  // 3. a case class that accepts a function as a parameter (what would happen ? )
  {
    case class F( f: Int => Int) // the case class that expects a function as parameter

    def f(a:Int) = {a + a} // function that returns back in two time more than it gets

    val obj = F(f)
    val f_ref = obj.f // actually.. it is a getter that returns a function. works like expected.
    println("result:"  + f_ref(2))

    // and how patter-matching will work with it?
    obj match {
      case F( f:(Int=>Int) ) => println(" f:(Int=>Int) matched")  // works as expected
    }
    obj match {
      case F( f: (Any=>Any) ) => println(" f:(Any=>Any) matched")  // works as expected
    }
    obj match {
      case F( _ ) => println("_ matched")                         // works as expected
    }
    /*
    obj match {
      case F( 4 ) => println("will not work") // it expects a function, no a Int value (or function result)
    }*/

  }

}
