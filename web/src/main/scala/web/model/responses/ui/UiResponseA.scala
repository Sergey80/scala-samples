package web.model.responses.ui

import web.model.responses.downstream.CommonDownstreamResponse

class UiResponseA(input: CommonDownstreamResponse) extends CommonUiResponse(input) {

  val prop3: String = input.prop1 + input.prop2

}


