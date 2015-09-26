package json.playjson

import play.api.libs.json._

// The Play JSON library

// https://www.playframework.com/documentation/2.4.x/ScalaJson


object StringToJsValue extends App {


  val jsonString = s"""{
    "name" : "Watership Down",
    "location" : {
      "lat" : 51.235685,
      "long" : -1.309197
    },
    "residents" : [ {
      "name" : "Fiver",
      "age" : 4,
      "role" : null
    }, {
      "name" : "Bigwig",
      "age" : 6,
      "role" : "Owsla"
    } ]
  }"""

  val json: JsValue = Json.parse(jsonString)

  // Traversing a JsValue structure

  val lat = (json \ "location" \ "lat").as[Double]
  val lat2 = (json \\ "lat").head.as[Double]    // Recursive path - returns array / JsArrays

  println(s"lat1: $lat")
  println(s"lat2: $lat2")


  println(json)

  /*
    JsValue to represent each valid JSON type:

    JsString
    JsNumber
    JsBoolean
    JsObject
    JsArray
    JsNull
   */
  
}
