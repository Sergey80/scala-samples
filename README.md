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

<b>HOW TO INSTALL:</b>

This is <b>SBT project</b>. So you may follow general <a href="http://www.scala-sbt.org/release/docs/index.html" target="_blank">rules</a> about how to open it in your ide or console.

For example To convert project to <b>IntellijIdea</b>:
<ol>
 <li> make sure you have this directory open (depending on your current sbt version): '.sbt/0.13/plugins' </li>
 <li> in that file '.sbt/0.13/plugins/build.sbt' make sure you have content like this: <br />
    <code>addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2")</code>
 </li>


    For more info: https://github.com/mpeltonen/sbt-idea
 </ol>

 Then usual steps are:
 <ol>
  <li>cd ~/<your-path>/scala-samples</li>
  <li>sbt</li>
  <li>gen-idea</li>
  <li>open project in IDE</li>
</ol>
