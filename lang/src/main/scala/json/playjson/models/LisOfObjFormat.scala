package json.playjson.models

// Shows how to convert to list of JSON with minimal effort. Using Json.format[T]
// https://www.playframework.com/documentation/2.5.x/api/scala/index.html#play.api.libs.json.Format

case class MyObject(name: String, num: Double)

object LisOfObjFormat extends App {

  import play.api.libs.json._
  implicit val myObjectFormat = Json.format[MyObject]

  val objectList = Seq(
                        MyObject("1", 1),
                        MyObject("2", 2),
                        MyObject("3", 3)
  )

  val jsObjects: JsObject = Json.obj("objects" -> objectList)

  println(jsObjects) // {"objects":[{"name":"1","num":1},{"name":"2","num":2},{"name":"3","num":3}]}

}
