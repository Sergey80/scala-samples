package webapp

// https://github.com/scala-js/scalajs-tutorial

import rx.Var

import scala.concurrent.Await
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

// DOM library:
import org.scalajs.dom
// is the root of the JavaScript DOM and corresponds to the global scope of JavaScript (aka the window object)
import org.scalajs.dom.document

// JQuery
import org.scalajs.jquery.jQuery

//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

//import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue


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

    def addClickedMessage(): Unit = {
      jQuery("body").append("<p>Hello World</p>")
    }

    def setupUI(): Unit = {
      jQuery("#click-me-button").click(addClickedMessage _)
    }

    // 4. Calling a service

    def callClient(): Unit = {

      val futurePosts = PostClient.posts()
      futurePosts foreach { posts =>
        posts foreach {post =>
          println(post.title)
          jQuery("#posts").append(s"<li>${post.title}</li>")
        }
      }

    }

    jQuery(setupUI _)

    callClient()

    // TODO: integration with ScalaRX
    // 5.
    //val text = Var(jQuery("#text").value().asInstanceOf[String])
  }
}
