package overriding

// #generic-method-overriding

// http://www.scala-lang.org/files/archive/spec/2.11/05-classes-and-objects.html
// 5.1.4 - Overriding

// TODO:move it another package and rename

object OverridePlus extends App {

  class A {
    def aa(a:A):String = "boo"
  }
  class B() extends A {
    // override def aa(a:B):String = "boo"    // ! you can NOT do this
  }
  
  // but there is a way

  trait A1 {
    type ChildOfA <: A             // not concrete (this makes all it abstract)
    def aa(a:ChildOfA):A = new B
  }

  class B1 extends A1 {
    type ChildOfA = B              // concrete type
    override def aa(b:B):B = new B          // ! now you CAN do it
  }
  
  val result2 = (new B1).aa(new B)

  println(result2.getClass.getSimpleName) // B

}
