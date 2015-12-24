import scalaz._
import Scalaz._

object ScalazDemo extends App {

  // Eq

  1 === 1

  //1 === "abc" // scalaz: wil fail

  1 == "abc" // scala: false
  1.some
  1.some =/= 2.some


  // Ord
  1 > 2.0 // scala False

  1 gt 2 // False
  //1 gt 2.0 // will fail

  1 max 2 // 2


  // Show - converts to str

  3.show // Cord apparently is a purely functional data structure for potentially long Strings
  3.shows //
  "hello".println

  // tanges

  'a' to 'e' // scala:  NumericRange(a, b, c, d, e)
  'a' |-> 'e' // List(a, b, c, d, e)

  'b'.succ // c
  1.succ
  //2

  // Functor for the scope
  var result = (1, 2, 3) map { _ + 1 } // (1,2,4)

}