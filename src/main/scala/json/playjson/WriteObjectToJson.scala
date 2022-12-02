package json.playjson

import json.playjson.models.{Resident, Location, Place}
import play.api.libs.json._
/*
  The Play JSON API provides implicit Writes for most basic types,
  such as Int, Double, String, and Boolean.
  It also supports Writes for collections of any type T that a Writes[T]
*/

/*
* To convert your own models to JsValues,
* you must define implicit Writes converters and provide them in scope.
*/

// https://www.playframework.com/documentation/2.4.x/ScalaJsonCombinators

// witter: writes object to json


object WriteObjectToJson extends App {

  val place = Place(
    "Watership Down",
    Location(51.235685, -1.309197),
    Seq(
      Resident("Fiver", 4, None),
      Resident("Bigwig", 6, Some("Owsla"))
    )
  )
  val jsonValue = Json.toJson(place)


  println(jsonValue)

  println( Json.prettyPrint(jsonValue) )

}
