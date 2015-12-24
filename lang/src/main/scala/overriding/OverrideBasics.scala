package overriding

// note: overriding & overloading happens by name and arguments
// like a() == a(), but a(p:Int) != a() != a(p:String)

object OverrideBasics extends App {
  
  class A {
    def foo():String = ""
  }

  // --- override
  
  class B extends A {
    override def foo() = ""       // need 'override' !
    def foo(a:Int) = ""           // no need 'override'  () != (_:Int)
  }

  // -- overloading

  def foo(a:String):String = "" // no name collision  _:Int != _:String
  //def foo(a:String):Int = 1   // name collision _:String != _:String

  def foo(a:A) = "a"
  def foo(a:B) = "b"            // no name collision _:A != _:B

  println ( foo(new A) )        // a
  println ( foo(new B) )        // b

}
