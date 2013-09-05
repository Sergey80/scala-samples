package apply_method

class ClassWithApplyMethod(x:Int) {

  def apply(): Int = {println("apply method is calling with no arguments"); 1}
  def apply(x: Int): Unit = {println("apply method is calling with '" + x + "' argument")}

}

object Starter extends App {

  val x = new ClassWithApplyMethod(1)


  println("-1-")

  val y = x(2) // equivalent to x.apply(2)


  println("-2-")

  //val z:Int = x  // this will not lead to apply() evaluation, because we do not use "()" to evaluate it

  val z:Int = x() // equivalent to x.apply()

  // in this sense apply() method is just default 'empty-name' when you apply "()" on a object

  // so apply is default function if we want to evaluate an object as a function

}

/* Output:
-1-
apply method is calling with '2' argument
-2-
apply method is calling with no arguments
 */
