// #implicit #implicit-methods

case class A(val n: Int)

object A {
  // just grouping implicits here
  implicit def aToString(a: A) : String = "A: %d" format a.n
}

val a = A(5)

val s1: String = a            // s == "A: 2"
// same as:
val s2:String = A.aToString(a)

// "aToString" method is called in attempt to case A to String. 
// This method is called implicitly


