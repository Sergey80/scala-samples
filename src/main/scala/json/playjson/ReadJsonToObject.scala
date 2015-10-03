package json.playjson

import play.api.libs.json._

import json.playjson.models._

object ReadJsonToObject extends App {

  // case class Location(lat: Double, long: Double)

  // or one line reader:  implicit val personReads = Json.reads[Location]
  // that would include all fields

  val jsonValue = Json.parse(""" {
                             "lat" : 51.235685,
                             "long" : -1.309197
                        }""")
  
  jsonValue.validate[Location] match {      // 'validate' converts and validate to Location
    case s: JsSuccess[Location] => {
      val location: Location = s.get         // Location(51.235685,-1.309197

      print(location)
      // do something with place
    }
    case e: JsError => {
      // error handling flow
    }
  }


}
