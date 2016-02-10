package web.model.responses.ui

import web.model.responses.downstream.DownstreamResponseA

case class UiResponseA(
                          prop1:Int,
                          prop2: String
                          ) extends CommonUiResponse


object UiResponseA {

  def from(downstreamResponseA: DownstreamResponseA): UiResponseA = {

    // make use of common conversion logic
    val commonUiResponse = CommonUiResponse.from(downstreamResponseA)

    UiResponseA(
      prop1 = commonUiResponse.prop1,  // coverts to Int
      prop2 = downstreamResponseA.prop2
    )
  }
}
