package web.model.responses.ui

import web.model.responses.downstream.{CommonDownstreamResponse, BDownstreamResponse, DownstreamResponseA}

import scala.util.Try

/*
  the vals are not initialized,
  because:
  1. for each subclass (case class) we DO want to see whole list of properties at once
  2. CommonUiResponse companion object is responsible for the class creating and filling tis fields by values
*/

trait CommonUiResponse {

  val prop1: Int

  val prop2: String

}


/**
 * CommonUiResponse companion object is responsible for the class creating and filling its fields by values
 */
object CommonUiResponse {

  def from(commonDownstreamResponse: CommonDownstreamResponse) : CommonUiResponse = {

    new CommonUiResponse {
      override val prop1: Int = commonDownstreamResponse.prop1.toInt // // converts to Int
      override val prop2: String = commonDownstreamResponse.prop2
    }
  }

}



