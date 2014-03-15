package closure

//# Closure

object ClosureSample extends App {

// Closure - is all about encapsulation

  object scope { // defining a scope to hide/close our data

    private var data = 1 // here we 'close' data in a scope

    def setData(value:Int) = { // so that setScope has access to it
      data = value                // using it, by setting new value to it ..
      data
    }

  }
  
  val setData : (Int=>Int) = scope.setData  // tacking ref to the function as Function Literal (just of convenience)

  val result = myFunction( setData, 2 ) // so when we pass this function as an argument

  def myFunction(f: Int=>Int, p:Int) = { 
    f(p)                                 // it will able to have access to 'data' and change its value
                                // so 'f' ('setData') functions keeps access to 'data'
                                // even though 'data' is hidden/closed - it closes it
  }
  
  println ( result  ) // so that we see change result

// -- In Java and other ancient languages you would usually do this (but that is not closure)

  abstract class ScopeSetter {  // define interface - we have to define it
    def setData(value:Int):Int      // since we want expose setData
  }                                   // since we are not going to pass function as an parameter
  class ScopeSetterImpl extends ScopeSetter { // one of possible implementations
    private var data = 1       // our private data
    override def setData(value: Int) = {
      data = value             // changing that data
      data
    }
  }

  val obj:ScopeSetter = new ScopeSetterImpl // ref to base interface

  val result2 = myFunction(obj) // here we passing reference to the object, but not to the function

  def myFunction(setter: ScopeSetter) = { // expecting an instance of ScopeSetter
    obj.setData(2) // so we can call setData() method on it
  }

  println( result2 )


// --- But just a reminding, in Scala function is an instance of Function class which has apply method

  object scope2 {

    var data = 1

    def setData = new Function1[Int, Int] { // function - is an object ! that has
      def apply(value: Int): Int = { // an interface with apply() method in it
        data = value
        data
      }
    }

  }

  // so...the same

  val result3 = myFunction(scope2.setData, 2) // FYI: 'setData' is a function an object (with known interface) at the same time

  def myFunction3(f:(Int=>Int), p:Int) = { // That's it - function is already 'interface', so no need to create one
    f(p)
  }

  println (result3)

  /*
   So.. The closure is ability of function to keep/remembering
   all stuff (val, vars..) from the scope where this function was defined
   and keep remember this in the scope where this function is executing.

   Same as it is done in OOP word - with encapsulation and polymorphic access.

   That's being said, it makes possible to implement design patterns like:
   Strategy, Visitor, Command, Functor, Callback/Listener  etc.
   in light way manner - with no interfaces and classes function way.
 */

}
