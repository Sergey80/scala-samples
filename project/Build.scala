import sbt._
import sbt.Keys._

object MyBuild extends Build {

  val dependencies = Seq(
    "org.specs2" %% "specs2" % "2.3.11" % "test",
    "org.scalatest" %% "scalatest" % "2.1.5",
    "org.scalaz" %% "scalaz-core" % "7.1.0",
    "org.scalaz" %% "scalaz-effect" % "7.1.0",
    "org.scalaz" %% "scalaz-typelevel" % "7.1.0",
    "joda-time" % "joda-time" % "2.8.2",
    "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
    "org.scalaz" %% "scalaz-scalacheck-binding" % "7.1.0" % "test",
    "com.typesafe.akka" % "akka-actor_2.11" % "2.3.12",
    "com.typesafe.akka" %% "akka-slf4j" % "2.3.6",
    "org.slf4j" % "slf4j-log4j12" % "1.5.2",
    "ch.qos.logback" % "logback-classic" % "1.0.9",
    "com.storm-enroute" %% "scalameter" % "0.7",
    "com.typesafe.play" % "play-json_2.11" % "2.3.10",
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.6.1"
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

    libraryDependencies ++= dependencies
  )

  lazy val statistics = Project(id = "Web", base = file("web")) settings(
    version       := "0.1",
    scalaVersion  := "2.11.7",

    libraryDependencies ++= dependencies
  )

  lazy val scalaSamples = Project(id = "ScalaSamples", base = file(".")) aggregate(lang, statistics)

}