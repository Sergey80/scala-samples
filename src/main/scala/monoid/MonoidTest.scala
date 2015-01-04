package monoid

// Definition:

/*
- In math, Monoid is a category with one object.
- In abstract algebra, a branch of mathematics, a Monoid is an _algebraic structure_ with
a single associative binary operation and an identity element.
*/

trait Monoid[T] {
  def append(m1: T, m2: T): T       // here is a single binary operation
  val identity: T                   // this is identity element
}

// Usage:

/* 
It is an algebraic structure - often what we need to write useful polymorphic functions
helps: breaking the problems by chunks facilita
*/

object MonoidTest extends App {

  val stringMonoid = new Monoid[String] {
    def append(s1: String, s2: String) = s1 + s2
    val identity = ""
  }

  println( stringMonoid.append("a", "b") )  // ab
  
}
