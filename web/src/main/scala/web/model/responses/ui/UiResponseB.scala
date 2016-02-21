package web.model.responses.ui

import web.model.responses.downstream.CommonDownstreamResponse

class UiResponseB(input: CommonDownstreamResponse) extends CommonUiResponse(input) {

  val prop4: String = input.prop1 + input.prop2

}


