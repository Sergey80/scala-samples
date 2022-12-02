package collections

// Interesting to know that Seq[T] is a function Function1[Int, T]

object SeqFun extends App {

  def first(f: Int => Int) = {
   f(0)
  }

  def second(f: Int => Int) = {
    f(1)
  }

  val s = Seq[Int](1,2,3)

  val restul_1 = first(s)
  val restul_2 = second(s)

  println(restul_1)
  println(restul_2)

  // actually it is partial function

  println( "isDefined: " + s.isDefinedAt(2) ) // true
  println( "isDefined: " + s.isDefinedAt(3) ) // false
}
