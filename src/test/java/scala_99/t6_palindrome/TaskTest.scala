package scala_99.t6_palindrome

import org.specs2.mutable._
import scala_99.t6_palindrome.Task._

class TaskTest extends Specification {

  "1,2,3" should {
    "be false" in {
      isPalindrome(List(1,2,3) ) must beFalse
    }
  }

  "1,2,1" should {
    "be true" in {
      isPalindrome(List(1,2,1) ) must beTrue
    }
  }



}
