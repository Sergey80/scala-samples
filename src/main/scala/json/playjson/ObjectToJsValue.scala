package json.playjson

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

case class Location(lat: Double, long: Double)
case class Resident(name: String, age: Int, role: Option[String])
case class Place(name: String, location: Location, residents: Seq[Resident])

object Place {

  object JsonWriters {  // Writers are needed to convert an Object/Type to JsonValue

    implicit val locationWrites = new Writes[Location] {
      def writes(location: Location) = Json.obj(
        "lat" -> location.lat,
        "long" -> location.long
      )
    }

    implicit val residentWrites = new Writes[Resident] {
      def writes(resident: Resident) = Json.obj(
        "name" -> resident.name,
        "age" -> resident.age,
        "role" -> resident.role
      )
    }

    implicit val placeWrites = new Writes[Place] {
      def writes(place: Place) = Json.obj(
        "name" -> place.name,
        "location" -> place.location,
        "residents" -> place.residents)
    }

  }


}

object ClassToJsonValue extends App {

  val place = Place(
    "Watership Down",
    Location(51.235685, -1.309197),
    Seq(
      Resident("Fiver", 4, None),
      Resident("Bigwig", 6, Some("Owsla"))
    )
  )

  import Place.JsonWriters._

  val json = Json.toJson(place)


  println(json)

  println( Json.prettyPrint(json) )

}
