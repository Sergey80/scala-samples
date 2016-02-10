package web.model.responses.ui

import web.model.responses.downstream.BDownstreamResponse

// Avoid putting default values for the field
// OptUiResponse s responsible for OptUiResponse creation

case class UiResponseB (
                          propOpt:  String,
                          // --
                          prop1:  Int,
                          prop2:  String
                        ) extends CommonUiResponse



object UiResponseB {

  def apply(optDownstreamResponse: BDownstreamResponse): UiResponseB = {

    val commonUiResponse = CommonUiResponse.from(optDownstreamResponse)

    UiResponseB( // TODO: that is too verbose !

      propOpt = "sdfsd",                // some specific to OPT UI only field

      // --
      prop1 = commonUiResponse.prop1,
      prop2 = commonUiResponse.prop2
    )
  }

}