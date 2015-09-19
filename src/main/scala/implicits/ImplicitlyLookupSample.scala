package implicits

// https://issues.scala-lang.org/browse/SI-8849

/**
 * " Nasty gotcha.
 *   Here's the general implicit search behaviour in question: "
*/

class A
class B extends A
class C extends B

//class C extends A  // NOTE: that would not work because we can NOT
                     // put two same-level instance of A into implicit scope

object ImplicitlyLookupSample extends App {

  implicit val c: C = new C     // there are two instances of class A
  implicit val b: B = new B     // they are put into implicit scope (somewhere globally)

  // But C is on the bottom of hierarchy:
  // A
    // - B
      // - C

  {
    //implicit val a: A = new B

    // we are asking for an instance of class A

    // so we wonder who is who? The guy who closer to A (B) or guys who is more far (C)?

    val who = implicitly[A]/*(c)*/             // implicitly - get instance of [..] implicitly

    println ("who: " + who.getClass)  // class implicits.C

    // The answer is "C"  !

    // So, if someone asks for instance of A in imp context it would lead to C :
    // A
    // - B
    // - C        <- [A]

    // -- note: about 'implicitly' (syntactic sugar)

    // we use 'implicitly' because, otherwise we should create our own function, like:
    def getA(implicit a:A) = a

    val who2 = getA

    println ("who2: " + who2.getClass)  // class implicits.C
  }

}

