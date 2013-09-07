package lazy_test

/*
 #lazy-val
*/
// sequence: #1

object LazyVal extends App {

  def fn(x:Int) = {println(x)}

  val v1 = fn(1)  // will print "1"
  lazy val v2 = fn(2) // will print nothing ! Because if v2 lazy then no even need to evaluate the fn to get v2 initialized

  println("here we are")

  // but if I try to use v2..
  v2 // '2' will print

  v2 // calling it second time - noting will be printed (because it was already evaluated)
}
