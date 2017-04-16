package tests99.test06_palindrome

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import tests99.test05_reverse.ReverseSpec

class PalindromeSpec extends FlatSpec with GeneratorDrivenPropertyChecks with Matchers {

  def palindrome[T](list: List[T]): Boolean = {
    new ReverseSpec().reverse(list) == list
  }

  assert ( palindrome(List()) === true)
  assert ( palindrome(List(1)) === true)
  assert ( palindrome(List(1,1)) === true)
  assert ( palindrome(List(1,2,3,2,1)) === true)
//
  assert ( palindrome(List(1,2)) === false)
  assert ( palindrome(List(1,2,2)) === false)


}
