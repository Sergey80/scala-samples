package implicits

object SimpleSample extends App {

  implicit val a = 2:Int

  def fun1(p1:Int = 1)(implicit p2:Int = 1): Int = {
    p1 + p2
  }

  println ( fun1() )      // 3
  println ( fun1(2) )     // 4
  println ( fun1(2)(3) )  // 5

}
