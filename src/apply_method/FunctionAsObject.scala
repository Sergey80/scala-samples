package apply_method

// #3 (look at #1, #1 first)

object FunctionAsObject extends App {

  def sum = (x:Int, y:Int) => x + y

  println(sum) // <function2>

  val v1 = sum.apply(1, 2) // demonstrate that function "sum" is an instance of Function2 that has method apply(_,_)

  println(v1)  // 3

}
