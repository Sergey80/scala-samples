package scala_99

import org.scalatest._

import scala_99.t6_palindrome._

// #scala-test

class t6palindromeTest_Specs2 extends FlatSpec with Matchers {

  "1" should "should be true" in {
    isPalindrome_v1(List(1) ) should be (true)
    isPalindrome_v2(List(1) ) should be (true)
  }

  "1,2,3" should "should be false" in {
    isPalindrome_v1(List(1,2,3) ) should be (false)
    isPalindrome_v2(List(1,2,3) ) should be (false)
  }

  "1,2,1" should "should be true" in {
    isPalindrome_v1(List(1,2,1) ) should be (true)
    isPalindrome_v2(List(1,2,1) ) should be (true)
  }

  "1,2,2,1" should "should be true" in {
    isPalindrome_v1(List(1,2,2,1) ) should be (true)
    isPalindrome_v2(List(1,2,2,1) ) should be (true)
  }


}
