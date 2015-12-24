package apply_method

/*
  #apply-method
  related: #lazy-evaluation #anonymous-function
*/
// sequence: #3 (look at #1, #2 first)

object FunctionAsObject extends App {

  def sum = (x:Int, y:Int) => x + y // #anonymous-function related

  println(sum) // <function2>   // #lazy-evaluation related

  val v1 = sum.apply(1, 2) // demonstrates that function "sum" is an instance of Function2 that has method apply(_,_)

  println(v1)  // 3


  // only because Function<N> has apply() method we can do this:
  val v2 = sum(2,2) // this is apply method !
  println(v2)  // 4

  // That means that in Scala everything tends to be a function (apply methods helps to get this feeling), but all functions are objects.

}
