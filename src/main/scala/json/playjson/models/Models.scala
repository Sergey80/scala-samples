package json.playjson.models

import play.api.libs.json.{JsPath, Reads, Json, Writes}

import play.api.libs.functional.syntax._


case class Location(lat: Double, long: Double)
case class Resident(name: String, age: Int, role: Option[String])
case class Place(name: String, location: Location, residents: Seq[Resident])

/*
   Set of companion objects.

   So all implicits defined in object's scope wll be available fo case classes,
   so that,

    Json.toJson(classInstance) will able to find proper writer

   and

    jsonValue.validate[Class] wll able to find proper reader
*/

object Location {

  // minimalistic:
  implicit val locationWrites = Json.writes[Location]

  // same as:
  /*
  implicit val locationWrites = new Writes[Location] {
    def writes(location: Location) = Json.obj(
      "lat" -> location.lat,
      "long" -> location.long
    )
  }*/


  // minimalistic:
  implicit val locationReads = Json.reads[Location]

  // same as:
  /*
  implicit val locationReads: Reads[Location] = (
    (JsPath \ "lat").read[Double] and
      (JsPath \ "long").read[Double]
    )(Location.apply _)
  */

}

object Resident {
  // minimalistic:
  implicit val residentWrites = Json.writes[Resident]

  // same as:
  /*
  implicit val residentWrites = new Writes[Resident] {
    def writes(resident: Resident) = Json.obj(
      "name" -> resident.name,
      "age" -> resident.age,
      "role" -> resident.role
    )
  }*/

}

object Place {

  implicit val placeWrites = Json.writes[Place]

  // same as:
  /*
  implicit val placeWrites = new Writes[Place] {
    def writes(place: Place) = Json.obj(
      "name" -> place.name,
      "location" -> place.location,
      "residents" -> place.residents)
  }
  */


}



