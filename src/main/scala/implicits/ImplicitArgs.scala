// #implicitly #implicit

package implicits

object ImplicitArgs extends App {

  // version without implicit
  def multiply2(f: Int => Int) = f(2)  // apply 2

  // version with implicit
  def multiply2_v2(f: Int => Int) = f(implicitly[Int])
  //implicitly = "inject Int value from the implicit scope"

  val result1 = multiply2( x => x * 2) // 2 is 'hardcoded' value here

  implicit val integer: Int = 3          // registering integer value in the implicit scope
  // implicit val integer2 = 4      // error: ambiguous implicit values

  val result2 = multiply2_v2(x => x * 2)

  println (result1) // output: 2 * 2 = 4
  println (result2) // output: 2 * 3 = 6

  // so, the advantage of using 'implicitly'
   // - we are expecting that somewhere in the implicit scope integer value is defined
   // so it works like a configuration by Type

  // --

  def function3(f: Int => Int) (implicit x:Int) = {
    f(x) // x=3
  }

  var result3_1 = function3(x => x * 2)  // we may use second parameter it is injected by default
  var result3_2 = function3(x => x * 2)(4)

  println( result3_1 ) // 3 * 2 = 6
  println( result3_2 ) // 3 * 4 = 8

  // and you can not do this
  def function4(implicit i:Int) = i   // this works as default variable (ALMOST)
  // same as this
  def function4_1(i:Int = implicitly[Int]) = i   // implicit scope has Int = 3

  val result4 = function4         // should be without ()
  val result4_1 = function4_1()   // requires ()

  println("result4: " +result4)     // 3
  println("result4_1: " +result4_1) // 3


}
