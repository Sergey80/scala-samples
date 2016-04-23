package webapp

// https://github.com/scala-js/scalajs-tutorial

import scala.scalajs.js.JSApp

// DOM library:
import org.scalajs.dom
// is the root of the JavaScript DOM and corresponds to the global scope of JavaScript (aka the window object)
import org.scalajs.dom.document

// JQuery
import org.scalajs.jquery.jQuery

object ScalaJsSample1 extends JSApp {
  def main(): Unit = {

    // 1.
    println("Hello world!")                   //

    // 2. DOM
    /*
    def appendPar(targetNode: dom.Node, text: String): Unit = {
      val parNode = document.createElement("p")
      val textNode = document.createTextNode(text)
      parNode.appendChild(textNode)
      targetNode.appendChild(parNode)
    }
    appendPar(document.body, "Hello World")
    */

    // 3. JQuery
    jQuery("body").append("Hello World")
  }
}
