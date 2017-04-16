package tests99.test03_getByIndex

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.GeneratorDrivenPropertyChecks

class FindKthSpec extends FlatSpec with GeneratorDrivenPropertyChecks with Matchers {

  def kth[T](index: Int, list: List[T]): Option[T] = {

    def searchElement(list: List[T], currentIndex: Int = 0): Option[T] = {
      currentIndex match {
        case i if i == index => Some(list.head)
        case _ if list.isEmpty=> None
        case _ => searchElement(list.tail, currentIndex + 1)
      }
    }

    list match {
      case Nil => None
      case _ => searchElement(list)
    }
  }

  // test


  kth(2, List("2", "1", "3")) should equal (Some("3"))
  kth(1, List("2", "1", "3")) should equal (Some("1"))
  kth(0, List("2", "1", "3")) should equal (Some("2"))
  kth(2, List()) should equal (None)
  kth(99, List("2", "1", "3")) should equal (None)

}
