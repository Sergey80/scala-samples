scala-samples
=============

<h3>Preface</h3>
Sometimes we have to read some articles, forums, blogs to understand Scala features.
But if we want just remind ourselves something we already knew, then is better just look at code sample.
You could easily change it and play with it and see what's going on.

There are samples of Scala code that explains how to use some scala features.

This code is supposed to be isolated as much as possible - like if we wish to explain/remember one feature of Scala then we are not
going to use 5 more extra features for that (which might not be gotten yet by a reader of this code).

Each example has no more than 100 lines of code. Also there is tagging is used in order to show to the reader what feature is involved
for particular example. Like #feature-1 #feature-3.

"Use the course, Luke!" (R)

<h3>HOW TO INSTALL & RUN</h3>

sbt & nodejs should be installed

NOTE: as SBT project, the project/Build.scala defines the dependencies and sub-projects.
So, to build run a particular project, say ScalaJS:

Start sbt-console:

```sbt```

Switch to that project:

```project ScalaJS```

Run:

```run```

--
Spec: http://www.scala-lang.org/files/archive/spec/2.11/
