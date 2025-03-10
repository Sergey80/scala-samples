ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.6.3"

//libraryDependencies += "com.typesafe.play" %% "play-json" % "3.0.4"

val scalatestVersion = "3.3.0-SNAP4"
val monocleVersion = "3.0.0-M6"

lazy val root = (project in file("."))
  .settings(
    name := "scala_samples",
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % scalatestVersion,
      "org.scalatest" %% "scalatest" % scalatestVersion % Test,
      "org.scalatestplus" %% "mockito-4-6" % "3.2.14.0" % Test,
      "org.scalatestplus" %% "junit-4-13" % "3.2.14.0" % Test,
      "org.scalatestplus" %% "scalacheck-1-16" % "3.2.14.0" % Test,
      "com.disneystreaming" %% "weaver-cats" % "0.8.3" % Test,


//      "com.chuusai" %% "shapeless" % "2.3.12",
      "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",
      "joda-time" % "joda-time" % "2.12.5",
      //      "com.storm-enroute" %% "scalameter-core" % "0.21",
      "com.typesafe.play" %% "play-json" % "2.10.0",
      //      "com.typesafe.akka" %% "akka-actor-typed" % "2.10.0",
      "org.scalaz" %% "scalaz-core" % "7.4.0-M15",
      "com.github.julien-truffaut" %% "monocle-core" % monocleVersion,
      "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion,
      // to replace with circle:
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.18.2",

      // cats
      "org.typelevel" %% "cats-core" % "2.10.0",
      "org.typelevel" %% "cats-effect" % "3.5.1",
      "com.github.cb372" %% "cats-retry" % "3.1.0",

    )
  )
