package web.model.responses.ui

import web.model.responses.downstream.CommonDownstreamResponse

import CommonUiResponse._

abstract class CommonUiResponse(input: CommonDownstreamResponse) {

  val prop1: Int      = toProp1(input.prop1)

  val prop2: String   = input.prop2

}

object CommonUiResponse {

  def toProp1(prop1:String) : Int = {
    prop1.toInt
  }

}



