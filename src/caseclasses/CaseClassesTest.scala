package caseclasses

/*
 #cace-classes
 related: #apply-method
*/
object CaseClassesTest extends App {

   case class A(a:Int, b:Int)

   val a1 = A(1,2)  // may omit using "new" (it comes with default apply(_,_) method

   println(a1) // toString() is defined
   println(a1.a) // public value

   val a2 = A(1,2)

   if (a1 == a2) println ("equal!")  // '==' (and 'equals') function is overloaded to use all fields to compare with

   //a1.a = 1 // can no do it (read only)

  case class B(var a:Int, var b:Int)

  val b1 = B(1,2)
  b1.a=2  // that's ok, because it was defined as VAR

  // --

  // works with "pattern matching"

  val b2 = B(1,2)
  var str = b2 match {
    case B(1,_) => "yes, first param is '1'"  // it works because of apply-method
  }
  println(str)

  // inheritance
  {
    case class A(a:Int)
    //case class A2(a:Int) extends A(1)               // does all us to extend, asks for 'val/val' and 'override'
    //case class A2(override val a:Int) extends A(1)    // but, but it still does not allow. It is prohibited to use inheritance !

  }

}
