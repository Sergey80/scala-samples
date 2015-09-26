package json.playjson

import play.api.libs.json._
import play.api.libs.functional.syntax._

object FromJsValueToModel extends App {

  // case class Location(lat: Double, long: Double)

  implicit val locationReads: Reads[Location] = (
    (JsPath \ "lat").read[Double] and
      (JsPath \ "long").read[Double]
    )(Location.apply _)

  val json = Json.parse(""" {
                             "lat" : 51.235685,
                             "long" : -1.309197
                        }""")

  json.validate[Location] match {      // 'validate' converts and validate to Location
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
