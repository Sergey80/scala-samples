import sbt._
import sbt.Keys._
import sbt.project
import sbt.Plugins._
import sbt.project



//import ScalaJSPlugin._
//import ScalaJSPlugin.autoImport._

//import org.scalajs.sbtplugin.cross.{CrossType, CrossProject}
import sbt._
import Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object BuildProject extends Build {


  val akkaDependencies = Seq(
    "com.typesafe.akka" % "akka-actor_2.11" % "2.4.2",
    "com.typesafe.akka" %% "akka-slf4j" % "2.3.6",
    "com.typesafe.akka" % "akka-stream_2.11" % "2.4.2"
  )

  val scalazDependencies = Seq(
    "org.scalaz" %% "scalaz-core" % "7.1.0",
    "org.scalaz" %% "scalaz-effect" % "7.1.0",
    "org.scalaz" %% "scalaz-typelevel" % "7.1.0",
    "org.scalaz" %% "scalaz-scalacheck-binding" % "7.1.0" % "test"
  )

  val coreDependencies = Seq(
    "org.specs2" %% "specs2" % "2.3.11" % "test",
    "org.scalatest" %% "scalatest" % "2.1.5",
    "joda-time" % "joda-time" % "2.8.2",
    "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
    "org.slf4j" % "slf4j-log4j12" % "1.5.2",
    "ch.qos.logback" % "logback-classic" % "1.0.9",
    "com.storm-enroute" %% "scalameter" % "0.7",
    "com.typesafe.play" % "play-json_2.11" % "2.3.10",
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.6.1"
  )

  lazy val scalaJsDependencies =  Seq(
    "com.lihaoyi" %% "scalarx" % "0.3.1"
  )


  lazy val lang = Project("Lang", file("lang")) settings(
    version       := "0.5",
    scalaVersion  := "2.11.7",

    scalacOptions in Test ++= Seq("-Yrangepos"),
    scalacOptions += "-feature",

    initialCommands in console := "import scalaz._, Scalaz._",

    testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),

    resolvers ++= Seq(
      "Repo1" at "http://oss.sonatype.org/content/repositories/releases",
      "Repo2" at "http://repo1.maven.org/maven2"
    ),

    //resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

    libraryDependencies ++= coreDependencies,
    libraryDependencies ++= akkaDependencies,
    libraryDependencies ++= scalazDependencies
  )

  lazy val akka = Project(id = "Akka", base = file("akka")) settings(
    version       := "0.1",
    scalaVersion  := "2.11.7",

    libraryDependencies ++= coreDependencies,
    libraryDependencies ++= akkaDependencies
   )


  lazy val scalaJS = Project(id = "ScalaJS", base = file("scalajs")).enablePlugins(ScalaJSPlugin).settings(
     version      := "0.1",
     scalaVersion := "2.11.7",

    ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }, // TODO:

     //mainClass := Some("branch.ScalaJsSample"),

     libraryDependencies ++= scalaJsDependencies,

     libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.0",
     libraryDependencies += "com.lihaoyi" %%% "upickle" % "0.4.0",

     // we will not use use DOM directly so commenting it
     // libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.0",


     jsDependencies += "org.webjars" % "jquery" % "2.2.3" / "jquery.js", // "jsDependencies" reserved

//     // After reloading and rerunning fastOptJS,
//        // this will create scala-js-jsdeps.js
     skip in packageJSDependencies := false,
     jsDependencies += "org.webjars" % "jquery" % "2.2.3" / "2.2.3/jquery.js",

    // allows DOM be available from from console' run (so no "ReferenceError: "window" is not defined." error would appear)
    jsDependencies += RuntimeDOM, // it will use PhantomJS, basically

     scalaJSUseRhino in Global := false //will use node.js to build the thing
    )

  lazy val scalaSamples = Project(id = "ScalaSamples", base = file(".")) aggregate(lang, akka, scalaJS)

}