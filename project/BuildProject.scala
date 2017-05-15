import sbt._
import Keys.{libraryDependencies, _}
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object BuildProject extends Build {


  val akkaDependencies = Seq(
    "com.typesafe.akka" %% "akka-actor" % Versions.akkaActor,
    "com.typesafe.akka" %% "akka-slf4j" % Versions.slf4jAkka,
    "com.typesafe.akka" %% "akka-stream" % Versions.akkaActor
  )

  val sparkDependencies = Seq(
    "org.apache.spark" %% "spark-core" % Versions.spark
  )


  val scalazDependencies = Seq(
    "org.scalaz" %% "scalaz-core" % Versions.scalaz,
    "org.scalaz" %% "scalaz-effect" % Versions.scalaz,
    "org.scalaz" %% "scalaz-scalacheck-binding" % Versions.scalaz % "test"
  )

  val coreDependencies = Seq(
    "org.specs2" %% "specs2" % Versions.specs2 % "test",
    "org.scalatest" %% "scalatest" % Versions.scalaTest,
    "joda-time" % "joda-time" % Versions.jodaTime,
    "org.scalacheck" %% "scalacheck" % Versions.scalacheck % "test",
    "org.slf4j" % "slf4j-log4j12" % Versions.slf4j,
    "ch.qos.logback" % "logback-classic" % Versions.logback,
    "com.storm-enroute" %% "scalameter" % Versions.scalameter,
    "com.typesafe.play" %% "play-json" % Versions.playJson,
    "com.typesafe.play" %% "play-json" % Versions.playJson,
    "com.typesafe.play" %% "play-json" % Versions.playJson,
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % Versions.jacksonModuleScala,
    "com.chuusai" %% "shapeless" % Versions.shapeless,

      "com.github.julien-truffaut" %%  "monocle-core"  % Versions.monocleVersion,
      "com.github.julien-truffaut" %%  "monocle-macro" % Versions.monocleVersion,
      "com.github.julien-truffaut" %%  "monocle-law"   % Versions.monocleVersion % "test"
  )

  lazy val scalaJsDependencies =  Seq(
    "com.lihaoyi" %% "scalarx" % Versions.scalaRx
  )


  lazy val algorithms = Project("algorithms", file("algorithms")) settings(
    version       := "0.5",
    scalaVersion  := Versions.scala,

    libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.5" % "test",
    libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0"
  )


  lazy val lang = Project("Lang", file("lang")) settings(
    version       := "0.5",
    scalaVersion  := Versions.scala,

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
    scalaVersion  := Versions.scala,

    libraryDependencies ++= coreDependencies,
    libraryDependencies ++= akkaDependencies
   )

  lazy val spark = Project(id = "spark", base = file("spark")) settings(
    version       := "0.1",
    scalaVersion  := Versions.scala,

    parallelExecution in test := false,

//    libraryDependencies ++= coreDependencies,
    libraryDependencies ++= sparkDependencies
  )


  lazy val scalaJS = Project(id = "ScalaJS", base = file("scalajs")).enablePlugins(ScalaJSPlugin).settings(
     version      := "0.1",
     scalaVersion := Versions.scala,

    ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }, // TODO:

     //mainClass := Some("branch.ScalaJsSample"),

     libraryDependencies ++= scalaJsDependencies,

     libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.0",
     libraryDependencies += "com.lihaoyi" %%% "upickle" % Versions.upickle,

    libraryDependencies += "com.lihaoyi" %%% "scalatags" % Versions.scalaTags,

     // we will not use use DOM directly so commenting it
     libraryDependencies += "org.scala-js" %%% "scalajs-dom" % Versions.dom,


     jsDependencies += "org.webjars" % "jquery" % Versions.jquery / "jquery.js", // "jsDependencies" reserved

//     // After reloading and rerunning fastOptJS,
//        // this will create scala-js-jsdeps.js
     skip in packageJSDependencies := false,
     jsDependencies += "org.webjars" % "jquery" % Versions.jquery / s"${Versions.jquery}/jquery.js",

    // allows DOM be available from from console' run (so no "ReferenceError: "window" is not defined." error would appear)
    jsDependencies += RuntimeDOM, // it will use PhantomJS, basically

     scalaJSUseRhino in Global := false //will use node.js to build the thing
    )

  lazy val scalaSamples = Project(id = "ScalaSamples", base = file(".")).settings(
    name:="ScalaSamples",
    version := Versions.binding
  ).aggregate(lang, akka, scalaJS, algorithms)

}