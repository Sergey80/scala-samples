package webapp

import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.ext.Ajax
import webapp.model.Post

import scala.concurrent.Future
import scala.scalajs.js.annotation.JSExport

import upickle.default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue


/**
  * Created by User on 4/25/2016.
  */
@JSExport
object PostClient {

  val searchUrl = "http://jsonplaceholder.typicode.com/posts"

  @JSExport
  def posts() : Future[Seq[Post]] = {

    Ajax.get(searchUrl) map { (xhr: XMLHttpRequest) =>

      val posts = read[Seq[Post]](xhr.responseText)

       posts

    }

  }

}
