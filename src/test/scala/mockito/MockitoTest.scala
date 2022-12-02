package mockito

import org.junit.Test
import org.scalatestplus.mockito.MockitoSugar;
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers.{eq => eqTo, _}  // have to use alias (because eq)

// Shows how to use mocks with "MockitoSugar" from scalatest

class Service {

  def call(args:List[String]) : List[String] = {
    args.reverse
  }

}

class MockitoTest extends MockitoSugar {

  val serviceMock = mock[Service]

  @Test
  def test(): Unit = {

    // GIVEN
    when( serviceMock. call(List("one", "two")) ).
      thenReturn(List("123"))

    // WHEN
    val result = serviceMock.call(List("one", "two"))

    // THEN 1
    verify( serviceMock, times(1) ).
      call( eqTo(List("one", "two")) )

    // THEN 2
    assert(result == List("123"))
  }


}


