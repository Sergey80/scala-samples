scala-samples
=============

Sometimes we need read some articles, forums, blogs to understand Scala features.
But if we want just remind yourself something we already knew, then is better just look at code sample.
You could easily change it and play with it and see what's going on.

There are samples of Scala code that explains how to use some scala features.

This code is supposed to be isolated as much as possible - like if we wish to explain/remember one feature of Scala then we are not
going to use 5 more extra features for that (which might not be gotten yet by a reader of this code).

Each example has no more than 100 lines of code. Also there is tagging is used in order to show to the reader what feature is involved
for particular example. Like #feature-1 #feature-3.

"Use the course, Luke!" (R)

HOW TO INSTALL:

This is SBT project. So you may follow general rules about how to open it in your ide or console.

To convert project to IntellijIdea:
 1. make sure you have this directory open (depending on your current sbt version): '.sbt/0.13/plugins'
 2. in that file '.sbt/0.13/plugins/build.sbt' make sure you have content like this:
    addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2")

    For more info: https://github.com/mpeltonen/sbt-idea

 Then usual steps are:
 1. cd ~/<your-path>/scala-samples
 2. sbt
 3. gen-idea
 4. open project in IDE
