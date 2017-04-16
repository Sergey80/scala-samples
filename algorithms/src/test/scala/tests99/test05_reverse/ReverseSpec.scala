package tests99.test05_reverse

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

import scala.annotation.tailrec

class ReverseSpec extends FlatSpec with GeneratorDrivenPropertyChecks with Matchers{

  // code

  def reverse[T](list: List[T], newList: List[T] = List()): List[T] = {

    @tailrec
    def toNewList(ls: List[T], newList: List[T] = List()): List[T] = {
      ls match {
        case Nil => newList
        case head :: tail => toNewList(tail, head :: newList)
      }
    }

    list match {
      case Nil => list
      case _ :: Nil => list
      case _ => toNewList(list)
    }
  }

  // this is a test

  forAll { list: List[Int] => {
      reverse(list) should equal (list.reverse)
    }
  }

}
