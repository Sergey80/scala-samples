package generics

// #generic-method-overriding

// TODO:move it another package and rename

object GenericTest extends App {

  class A
  class B extends A

  trait AbstractStuff {
    type ChildOfA <: A             // !    (this makes it abstract, the rule)
    
    def stuff(f:A):A = new A
    
    def stuff2(f:ChildOfA):A = new B
  }

  class Stuff extends AbstractStuff {
    type ChildOfA = B              // !    here we define concrete type
    
    // override def stuff(f:B):B = new B    // you can not do this
     
    override def stuff2(f:B):B = new B
  }
  
//  val result1 = (new AbstractStuff).stuff(new B)    // can not
  val result2 = (new Stuff).stuff(new B)


  println(result2.getClass.getSimpleName)

}
