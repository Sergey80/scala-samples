package pattern_matching


/*
 *  #pattern-matching #anonymous-function #partial-function
 * related:
*/
object PatternMatching_AnonymousFunction extends App {

  // 1. use pattern-matching as anonymous function

  // working with map, where type of Key and Value is defined

  val map = Map[Int,String](1->"A", 2->"B")

  map foreach {  case(k,v) => println("k="+k+";v="+v) }  // pattern-matching in action !

  // just reminding that foreach() is defined as: "def foreach[U](f: A => U):Unit "

  // If we did not use use pattern matching, then your code would look like this
  map.foreach(x => {
    if (x._1 == 1)  println("k=" + x._1 + ";v=" + x._2)
    else           println("k=" + x._1  + ";v=" + x._2)
  })

  // so, foreach expects a function

  // But what kind of function we have provided with " case(k,v) => println("k="+k+";v="+v) "  ?
  // It is:
  // (Int,String) => Unit i.e. Tuple2[Int, String] => Unit  ?
  // TODO: http://stackoverflow.com/questions/18807890/to-see-anonymous-function-declaration


  // 2.
  val list = List("a", "b", "c", 1, 2, 3) // here we use strings and integers in one line/list

  // will not compile :

//  list map { case (x:Int) => println(x+1) }  // try to increase all Int values

  // it is because our anonymous incrementing function can not be applied for String values that this list has
  // I.e. the data we have (integers and strings) deprives our anonymous function sense.

  // but this will work
  list collect{ case (x:Int) => print(x+1) }  // "234" - will work, because it accept PartialFunction

  // @see PartialFunctionTest ! to get explanation

}
