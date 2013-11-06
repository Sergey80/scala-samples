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

BEFORE: make sure than in your IDE, here "File->Setting->Plugins->Browse repositories":
 1. "Scala v.0.22.302" (> 83 000 downloads) plugin installed (ide plugin)
 2. "SBT v. 1.5.1" (>10 000 downloads)" plugin installed (ide plugin)

Those versions that I have right now (4 November 2013)



<ol>
 <li> make sure you have this directory open (depending on your current sbt version): <code>'.sbt/0.13/plugins'</code> </li>
 <li> in that file <code>'.sbt/0.13/plugins/build.sbt'</code> make sure you have content like this: <br />
    <code>addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2")</code>
 </li>


    For more info: <a href="https://github.com/mpeltonen/sbt-idea">sbt-idea plugin</a>
 </ol>

 Then usual steps are:
 <ol>
  <li>cd ~/[your-path]/scala-samples</li>
  <li>sbt</li>
  <li>gen-idea</li>
  <li>Open project in IDE - just open it as usual IntelliJ idea project</li>
</ol>
