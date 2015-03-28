name := "scala-samples"

version := "0.3"

scalaVersion := "2.11.6"

resolvers += "Repo1" at "http://oss.sonatype.org/content/repositories/releases"

resolvers += "Repo2" at "http://repo1.maven.org/maven2"


libraryDependencies ++= Seq(
    "org.specs2" %% "specs2" % "2.3.11" % "test",
    "org.scalatest" %% "scalatest" % "2.1.5",
    "org.scalaz" %% "scalaz-core" % "7.1.0",
    "org.scalaz" %% "scalaz-effect" % "7.1.0",
    "org.scalaz" %% "scalaz-typelevel" % "7.1.0",
    "org.scalaz" %% "scalaz-scalacheck-binding" % "7.1.0" % "test"
)

scalacOptions += "-feature"

initialCommands in console := "import scalaz._, Scalaz._"


scalacOptions in Test ++= Seq("-Yrangepos")

// Read here for optional dependencies:
// http://etorreborre.github.io/specs2/guide/org.specs2.guide.Runners.html#Dependencies

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)