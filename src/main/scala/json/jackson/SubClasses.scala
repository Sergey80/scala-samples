package json.jackson

// shows how to use subclasses when it comes to de/serialization to/from json

import java.io.StringWriter
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

// TODO: https://github.com/FasterXML/jackson-module-scala/issues/199
// at some point I had (not sure how I could reproduce it):
  // com.fasterxml.jackson.databind.JsonMappingException: Argument #0 of constructor [constructor for A$A0$A$A0$Group, annotations: [null]] has no property name annotation; must have name when multiple-parameter constructor annotated as Creator

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "aType")
@JsonSubTypes(Array(
  new Type(value = classOf[ModelA], name = "ModelA"),
  new Type(value = classOf[ModelB], name = "ModelB")
))
class BaseModel(val modelName:String)

//@JsonTypeName("SomeModel")  // Commented. Do I need this?
class ModelA(val a:String, val b:String, val c:String, commonData:String)  extends BaseModel(commonData) {
  def this() = this("default", "default", "default" ,"default")
}
//@JsonTypeName("SomeModel") // Commented. Do I need this?
class ModelB(val a:String, val b:String, val c:String, commonData:String)  extends BaseModel(commonData) {
  def this() = this("default", "default", "default" ,"default")
}

object IssueWithCaseClasses extends App {

  // 0. create a mapper
  val mapper = new ObjectMapper
  mapper.registerModule(DefaultScalaModule)

  // from json to Model
  {

    // 1. giving json string
    val jsonA =
      """
        | {
        |   "aType" : "ModelA",
        |   "commonData" : "commonData-value",
        |   "a" : "a-value",
        |   "c" : "c-value"
         | }""".stripMargin


    // 2. convert to object
    val model = mapper.readValue(jsonA, classOf[ModelA])

    println(model.a)
    println(model.b)
    println(model.c)
    println(model.modelName)
  }

// from model to json --------------

  {

    // 1.model
    val modelA = new ModelA("1", "2", "3", "4")
    val modelB = new ModelB("1", "2", "3", "4")

    // 2. writers
    val stringWriterA = new StringWriter
    val stringWriterB = new StringWriter
    mapper.writeValue(stringWriterA, modelA)
    mapper.writeValue(stringWriterB, modelB)

    // 3. to json
    val jsonA = stringWriterA.toString
    val jsonB = stringWriterB.toString

    println("backToJsonA: " + jsonA) // {"aType":"ModelA","a":"1","b":"2","c":"3","modelName":"4"}
    println("backToJsonB: " + jsonB) // {"aType":"ModelB","a":"1","b":"2","c":"3","modelName":"4"}
  }
}
