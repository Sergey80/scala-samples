package scala_99

import org.specs2.mutable._

import scala_99.t6_palindrome._

// #specs2

class t6palindromeTest_ScalaTest extends Specification {

  "1,2,3" should {
    "be false" in {
      isPalindrome_v1(List(1,2,3) ) must beFalse
      isPalindrome_v2(List(1,2,3) ) must beFalse
    }
  }

  "1,2,1" should {
    "be true" in {
      isPalindrome_v1(List(1,2,1) ) must beTrue
      isPalindrome_v2(List(1,2,1) ) must beTrue
    }
  }

  "1,2,2,1" should {
    "be true" in {
      isPalindrome_v1(List(1,2,2,1) ) must beTrue
      isPalindrome_v2(List(1,2,2,1) ) must beTrue
    }
  }



}
