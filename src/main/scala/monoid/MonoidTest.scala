package monoid

// Definition:

/*
- In math, Monoid is a category with one object.
- In abstract algebra, a branch of mathematics, a Monoid is an _algebraic structure_ with
a single associative binary operation and an identity element.
*/

trait Monoid[T] {
  def op(m1: T, m2: T): T       // here is a single binary operation
  val identity: T                   // this is identity element
}

// Usage:

/* 
It is an algebraic structure - often what we need to write useful polymorphic functions
helps: breaking the problems by chunks facilita
*/

object MonoidTest extends App {

  val stringAddMonoid = new Monoid[String] {
    def op(s1: String, s2: String) = s1 + s2
    val identity = ""
  }

  println( stringAddMonoid.op("a", "b") )  // ab
  
  val result = List("a", "b", "c").foldLeft(stringAddMonoid.identity)(stringAddMonoid.op)
  
  println (result)
  
  def concantenate[T] (list:List[T], monoid: Monoid[T]) : T = 
    list.foldLeft(monoid.identity)(monoid.op)    
  
   val intAddMonoid = new Monoid[Int] {
    def op(n1:Int, n2:Int) = n1 + n2
    val identity = 0
  }
  
  println ( concantenate( List(1,2,3), intAddMonoid) )           // 6
  println ( concantenate( List("1","2","3"), stringAddMonoid) )  // 123

}
