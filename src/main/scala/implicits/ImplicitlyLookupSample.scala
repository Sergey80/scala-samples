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

  // adding to implicit scope
  //          key  -> value
  implicit val a: A = new A
  implicit val b: A = new B
  implicit val c: B = new C

  // So C is on the bottom of hierarchy:
  // A
    // - B
      // - C

  {

    // we are asking for an instance of class A

    // so we wonder who is who? The guy who closer to A (B) or guys who is more far (C)?

    val who = implicitly[A]/*(c)*/             // implicitly - get instance of [..] implicitly

    println ("who: " + who.getClass)  // class implicits.C

    // The answer is "C"  !

    // So, if someone asks for instance of A as a KEY - it would lead to C :
    // A
      // - B
        // - C        <- [A]

    // 1. Client:
      // Give me a value by type A as key

    // 2. Implicit scope:
      // Ok, how many implementations of type A in the key row? "A" itself and "B"
      // who of them is less abstract? Who is a child? B! because B extends A
      // then B is a key - and it refers to - > C
      // here we go you get a C


// -- note: about 'implicitly' (syntactic sugar)

    // we use 'implicitly' because, otherwise we should create our own function, like:
    def getA(implicit a:A) = a

    val who2 = getA

    println ("who2: " + who2.getClass)  // class implicits.C
  }

}

