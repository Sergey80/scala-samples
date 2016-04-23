scala-js-samples
=============

"Use the course, Luke!" (R)

<h3>HOW TO INSTALL & RUN</h3>

sbt & nodejs should be installed

NOTE: as SBT project, the project/Build.scala defines the dependencies and sub-projects.
So, to build run a particular project, say ScalaJS:

Start sbt-console:

```sbt```

Switch to that project:

```project ScalaJS```

Run (will use NodeJS to run it):

```run```

To compile to JS:

```fastOptJS``

The result will be in: ../scalajs/target/scala-2.11/scalajs-fastopt.js

--
Spec: http://www.scala-lang.org/files/archive/spec/2.11/

See the doc for more details: https://www.scala-js.org/tutorial/basic/

Then run index.html in the browser, that would run/include scalajs-fastopt.js
so you would see the result of your generated js in your browser.