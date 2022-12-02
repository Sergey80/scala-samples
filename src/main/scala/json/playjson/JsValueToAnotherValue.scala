package json.playjson

import play.api.libs.json._

object JsValueToAnotherValue extends App {

  val json = Json.parse(
    s"""{
      "name" : "Watership Down",
      "location" : {
        "lat" : 51.235685,
        "long" : -1.309197
      }
  }""")

  val minifiedString: String = Json.stringify(json)
  val readableString: String = Json.prettyPrint(json)


  // The simplest way to convert a JsValue to another type is using JsValue.as[T](implicit fjs: Reads[T])
  val name = (json \ "name").as[String]

  println(s"name: $name")

  val nameOption = (json \ "name").asOpt[String]
  val nameOption1 = (json \ "name1").asOpt[String]

  print(nameOption1 + "\n") // None



println("----- Using validation --------")


  val nameResult: JsResult[String] = (json \ "name").validate[String]
  nameResult match {
    case s: JsSuccess[String] => println("Result Name: " + s.get)
    case e: JsError => println("Result Errors: " + JsError.toJson(e).toString())
  }

  val nameUpperResult: JsResult[String] = nameResult.map(_.toUpperCase)


  println(nameUpperResult) // JsSuccess(WATERSHIP DOWN,)


  // To convert from JsValue to a model, you must define implicit Reads[T] where T is the type of your model.


}
