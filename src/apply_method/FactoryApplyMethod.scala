package apply_method

/*
  #apply-method
  related: #factory-method #companion-object
*/
// sequence: #2 (look at #1 first)

// Shows general use case:how to use apply method as factory method

class A(x:Int)

class B(x:Int) // this class does not have companion object

// companion object
object A { // why do we use "object" here, but not "class"? because we want to use "()" method as class' constructor has

  def apply(x:Int) = new A(x) // #factory-method related

}

object FactoryApplyMethod extends App {

  val a1 = A(1)     // create instance of class A by calling apply method on A object

  //val b1 = B(2)   // this will not able to compile, we can NOT create an object without using "new"

  val a2 = new A(1) // same result by calling constructor

  // why the difference ?

  //  by using A(1) we do not use method "new" to create an object (it make code more precise/short). And an object looks like a function call


}
