package pattern_matching

/**
 * #pattern-matching
 * related:
 */
object PatternMatchingTest extends App {

  // 1.
  val x1 = 1

  val str1 = x1 match {
    case 1 => "one"
    case 2 => "two"
  }
  println (str1) // "one"

  // 2.
  val x2 = 2

  val str2 = x2 match {
    case x2 if (x2>1) => "more than one"  // yes you can put condition like this, and this is good
    case _ => "default"                 // wil fail in RUNTIME "scala.MatchError" if miss this part when x2 <= 1  !!!
  }                                     // so, the rule is all cases should be covered !

  println (str2) // prints: "more than one" if x=2. And prints: "default" when x2=1

  // 3. Nested cases. Will not work  TODO: ..
  /*
  {
    val x1 = 1
    val str = x1 match {   // scala.MatchError: 1 (of class java.lang.Integer)
      case x if(x > 1) => "x"+x match {case "x1" => "yes"}
    }
    println (str)
  }*/

  // 4. how it works with lists
  val list = List(1,2,3)
  list match {
    case List(_,_,3) => println("yes, there is '3' as a last element")
  }
  list match {
    case List(_,_) => println("yes, there are two element defined") // will NOT be printed because it match to 2 long args list
    case List(_) => println("could not find what to match 1") // will NOT be printed because it match to 1 long args list
    case List(_*) => println("could not find what to match 2") // will be printed because '*_' means any args
  }


  // 5. how it matches by type
  def function(x:Any):Unit = x match {

    case _:String => println("yes, this is string")

      case x:Number => println("no, this is NOT string, this is number..")

      // this will not be happen, because Number is first/top in the 'math'
      case _:Int => println("no, this is NOT string, this is Int..")

      case _ => println("who knows..")
  }
  function("123")
  function(123)

}
