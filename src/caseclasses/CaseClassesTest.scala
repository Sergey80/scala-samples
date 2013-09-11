package caseclasses

/*
 #case-classes
 related: #apply-method #unapply-method #extractor
*/
object CaseClassesTest extends App {

  case class A(a:Int, b:Int)

  // 1. may omit using "new"
  val a1 = A(1,2)  // same as A.apply(1,2)


  // 2. toString() is defined by default
  println(a1) // prints: A(1,2)


  // 3. public read-only(getters) properties by default
  println(a1.a) // so no need to put "val a:int" in arguments like for general classes

  //a1.a = 1 // can no do it (read only)


  // 4. equals() defined by default
  val a2 = A(1,2)
  if (a1 == a2) println ("equal!")  // method '==' uses default built-in 'equals()'

  // 5. you we want setters to be defined
  case class B(var a:Int, var b:Int) // we need to put 'var' (same as for general classes)
  val b1 = B(1,2)
  b1.a=2  // that's ok, because it was defined as VAR

  // --

  // 6. works with "pattern matching" - #unapply-method #extractor related

  val b2 = B(1,2)
  var str = b2 match {
    case B(1,_) => "yes, first param is '1'"  // it works because of unapply-method (extractor) method is defined by default
  }
  println(str)

  // 7. inheritance
  {
    case class A(a:Int)
    //case class A2(a:Int) extends A(1)               // does allow us to extend, asks for 'val/val' and 'override'
    //case class A2(override val a:Int) extends A(1)  // but it still does not allow. It is prohibited to use inheritance !


    // but
    class GeneralClass(val a:Int) // if we have general class (not case class)
    case class AA(override val a:Int) extends GeneralClass(1) // then we can extend our case class from it

    val g:GeneralClass = AA(2)

    println ("inherited, and overridden: " + g.a) // 2
  }

  // 8. a case class that accepts a function (what would happen ? )
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
      case F( f:(Any=>Any) ) => println(" f:(Any=>Any) matched")  // works as expected
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
