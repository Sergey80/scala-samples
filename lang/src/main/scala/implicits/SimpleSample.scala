package implicits

object SimpleSample extends App {

  implicit val a = 2:Int
  //implicit val b = 2:Int   // will fail - Only one Int is allowed (name of variable does not matter)

  def fun1(p1:Int = 1)(implicit p2:Int = 1): Int = {
    p1 + p2
  }

  //def fun2(implicit p1:Int = 1)(implicit p2:Int = 1): Int  // will not work - only one implicit is allowed

  println ( fun1() )      // 3
  println ( fun1(2) )     // 4
  println ( fun1(2)(3) )  // 5

}
