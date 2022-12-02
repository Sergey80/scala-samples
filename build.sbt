ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test"
libraryDependencies += "org.scalatestplus" %% "mockito-4-6" % "3.2.14.0" % "test"
libraryDependencies += "org.scalatestplus" %% "junit-4-13" % "3.2.14.0" % "test"
// https://mvnrepository.com/artifact/org.scalacheck/scalacheck
libraryDependencies ++= Seq(
  "org.scalatestplus" %% "scalacheck-1-14" % "3.1.1.1" % Test
)


libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.3"
)

libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"

libraryDependencies += "joda-time" % "joda-time" % "2.12.1"

libraryDependencies += "com.storm-enroute" %% "scalameter-core" % "0.20"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.3"



libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.7.0"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.3.7"

libraryDependencies += "com.github.julien-truffaut" %% "monocle-core" % "2.1.0"
libraryDependencies += "com.github.julien-truffaut" %% "monocle-macro" % "2.1.0"


libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.14.1"



lazy val root = (project in file("."))
  .settings(
    name := "scala_samples"
  )
