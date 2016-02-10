package web.services

import java.io.StringWriter
import web.model.requests.ui.UiRequestOrder
import web.model.responses.downstream.BDownstreamResponse
import web.model.responses.ui.{UiResponseB, CommonUiResponse}

import scala.util.Try

class OrderService {

  // TODO: Future[...]

  def submitOrder(order:UiRequestOrder): Try[CommonUiResponse] = Try {

    // assume that we did call to downstream-system and get response from it
    val downstreamResponse = BDownstreamResponse(prop1 = "1", prop2 = "2", prop3 = "3")

    UiResponseB(downstreamResponse) // covert downstream response to ui one

  }

}

// --

object OrderServiceTest extends App {

  val order = UiRequestOrder("B", 1.00)

  val service = new OrderService()

  val tryResponse = service.submitOrder(order)

  println("tryResponse: " + tryResponse)

  val response: CommonUiResponse = tryResponse.get

  println("response:" + response)

  // to JSON (with jackson)

    val scalaJsonMapper = {
      import com.fasterxml.jackson.databind.ObjectMapper
      import com.fasterxml.jackson.module.scala.DefaultScalaModule
      new ObjectMapper().registerModule(new DefaultScalaModule)
    }
  
    val stringWriter = new StringWriter
    scalaJsonMapper.writeValue(stringWriter, response)

    println("json: \n" + stringWriter.toString)

}
