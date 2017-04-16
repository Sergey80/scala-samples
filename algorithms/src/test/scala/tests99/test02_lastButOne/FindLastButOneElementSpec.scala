package tests99.test02_lastButOne

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.GeneratorDrivenPropertyChecks

import scala.annotation.tailrec

class FindLastButOneElementSpec extends FlatSpec with GeneratorDrivenPropertyChecks with Matchers {

  // code

  @tailrec
  final def lastButOne[T](list: List[T]): Option[T] = {
    list match {
      case Nil => None
      case _ :: Nil => None
      case head :: tail if tail.size == 1 => Some(head)
      case _ :: tail => lastButOne(tail)
    }
  }

  // test

  forAll { list: List[Int] => {
    if (list.size > 1)
      lastButOne(list) should equal(Some(list.reverse(1)))
    else
      lastButOne(list) should equal(None)
    }
  }

}
