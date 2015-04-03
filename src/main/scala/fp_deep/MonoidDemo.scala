package fp_deep
// #monoid

/*
- In math, Monoid is a category with one object.
- In abstract algebra, a branch of mathematics, a Monoid is an _algebraic structure_ with
a single associative binary operation and an identity element.
*/

trait Monoid[T] {
  def mappend(m1: T, m2: T): T

  // here is a single binary operation
  val mzero: T // this is identity element
}

object StringMonoid extends Monoid[String] {
  def mappend(s1: String, s2: String) = s1 + s2

  val mzero = ""
}

object IntMonoid extends Monoid[Int] {
  def mappend(n1: Int, n2: Int) = n1 + n2

  val mzero = 0
}


object MonoidDemo extends App {

  def sum[A](xs: List[A], m: Monoid[A]): A =
    xs.foldLeft(m.mzero)(m.mappend)

  val sumInt = sum(List(1, 2, 3, 4), IntMonoid) // 10
  val sumString = sum(List("a", "b", "c", "d"), StringMonoid)

  println(sumInt)
  println(sumString)

  // abcd

  // we may be implicit
  def sum2[A](xs: List[A])(implicit m: Monoid[A]): A =
    xs.foldLeft(m.mzero)(m.mappend)

  implicit val intMonoid = IntMonoid
  implicit val stringMonoid = StringMonoid

  sum2(List(1, 2, 3)) // 6
  sum2(List("a", "b", "c", "d"))

  // abcd


  // or we can avoid using 'implicit' in arguments, but use 'implicitly' inside the f body

  def plus[A: Monoid](a: A, b: A): A =
    implicitly[Monoid[A]].mappend(a, b) // I would call 'implicitly' 'inject'

  val plusInt = plus(1, 2)
  val plusString = plus("a", "b")

  println(plusInt)
  println(plusString)
}