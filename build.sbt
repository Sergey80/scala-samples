name := "scala-samples"

version := "0.4"

scalaVersion := "2.11.7"

resolvers += "Repo1" at "http://oss.sonatype.org/content/repositories/releases"

resolvers += "Repo2" at "http://repo1.maven.org/maven2"


libraryDependencies ++= Seq(
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
    "com.storm-enroute" %% "scalameter" % "0.7"
)

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

scalacOptions += "-feature"

initialCommands in console := "import scalaz._, Scalaz._"


scalacOptions in Test ++= Seq("-Yrangepos")

// Read here for optional dependencies:
// http://etorreborre.github.io/specs2/guide/org.specs2.guide.Runners.html#Dependencies

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)