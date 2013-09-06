package apply_method

/*
  #apply-method
*/

// sequence: #1 (look at #1, #2 after)

class ClassWithApplyMethod(x:Int) {

  def apply(): Int = {println("apply method is calling with no arguments"); 1}
  def apply(x: Int): Unit = {println("apply method is calling with '" + x + "' argument")}

}

object Starter extends App {

  val obj = new ClassWithApplyMethod(1) // no any apply() method will be invoked, because the object is not yet created at this moment


  println("-1-")

  val y = obj(2) // equivalent to x.apply(2)


  println("-2-")

  //val z:Int = x  // this will not lead to apply() evaluation, because we do not use "()" to evaluate it

  val z:Int = obj() // equivalent to x.apply()

  // in this sense apply() method is just default 'empty-name' function which we could call when we applying "()" on a object

  // so apply is default function if we want to evaluate an object as a function

}

/* Output:
-1-
apply method is calling with '2' argument
-2-
apply method is calling with no arguments
 */
