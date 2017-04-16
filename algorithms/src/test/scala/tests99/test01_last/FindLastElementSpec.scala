package tests99.test01_last

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

import scala.annotation.tailrec

class FindLastElementSpec extends FlatSpec with GeneratorDrivenPropertyChecks with Matchers {

   // code

  @tailrec
  final def last[T](list: List[T]): Option[T] = {
    list match {
      case Nil => None
      case head :: Nil => Some(head)
      case _ :: tail => last(tail)
    }
  }

  // test

  forAll { list: List[Int] => {
     if (list.nonEmpty)
      last(list) should equal(Some(list.last))
    if (list.isEmpty)
      last(list) should equal(None)
    }
  }

}
