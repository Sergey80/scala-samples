object Versions extends ScalaJVMVersions with ScalaJSVersions with SharedVersions with WebJarsVersions {
  val scala = "2.11.8"
  val binding = "0.5.0"
}

trait ScalaJVMVersions {

  val spark = "2.0.0"

  val akkaActor = "2.4.4"

  val jodaTime = "2.8.2"

  val scalacheck = "1.12.5"

  val scalameter = "0.7"

  val jacksonModuleScala = "2.6.1"

  val shapeless = "2.3.2"

  val playJson = "2.5.3"

  val scalaz = "7.2.2"

  val specs2 = "3.7"

  val logback = "1.0.9"

  val slf4j = "1.7.21"

  val slf4jAkka = "2.3.6"

//  val akkaHttp = "2.4.3"
//
//  val akkaHttpExtensions = "0.0.10"
//
//  val ammonite = "0.5.7"
//
//  val seleniumJava = "2.53.0"
//
//  val config = "1.3.0"
}

trait SharedVersions
{
  val scalaRx = "0.3.1" //val scalaRx = "0.3.1.1" //temporary published snapshot

  val scalaTags = "0.5.5"

  val monocleVersion = "1.4.0"

//  val scalaCSS = "0.4.0"
//
//  val macroParadise = "2.1.0"
//
    val scalaTest =  "3.0.0-M16-SNAP4"
//
//  val booPickle = "1.1.3"
//
//  val productCollections = "1.4.3"
//
//  val fastParse = "0.3.7"
}

trait ScalaJSVersions {

  val dom = "0.9.0"

  val jqueryFacade = "0.11"

  val semanticUIFacade = "0.0.1"

  val codemirrorFacade = "5.11-0.7"

  val threejsFacade =  "0.0.74-0.1.6"

  val upickle = "0.4.0"

}

trait WebJarsVersions {
  val jquery = "2.2.3"
}