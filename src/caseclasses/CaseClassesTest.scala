package caseclasses

/*
 #case-classes
 related: #apply-method #unapply-method #extractor #variable-binding
*/
object CaseClassesTest extends App {

   case class A(a:Int, b:Int)

   // 1. may omit using "new"
   val a1 = A(1,2)  // same as A.apply(1,2)


   // 2. oString() is defined by default
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
    //case class A2(a:Int) extends A(1)               // does all us to extend, asks for 'val/val' and 'override'
    //case class A2(override val a:Int) extends A(1)    // but, but it still does not allow. It is prohibited to use inheritance !


    // but
    class GeneralClass(val a:Int) // if we have general class (not case class)
    case class AA(override val a:Int) extends GeneralClass(1) // then we can extend our case class from it

    val g:GeneralClass = AA(2)

    println ("inherited, and overridden: " + g.a) // 2
  }

  // 8. you can define you variable binding inside 'case' ( #variable-binding related )
  {
    case class AA(a:Int, b:Int)
    val aa = AA(1,2)
    aa match {
      case AA(x @ myX, y) => println("myX: " + myX)
    }
  }

}
