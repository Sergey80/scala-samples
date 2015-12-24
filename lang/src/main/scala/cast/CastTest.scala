package cast

import scala.concurrent._
import scala.concurrent.duration._

import ExecutionContext.Implicits.global

trait Order {
  var aType:String = _
}

class OrderB extends Order {
  aType = "b"
}
class OrderC extends Order {
  aType = "c"
}

class Client {
  def send(payload:String): Future[String] = {
    Future { "bright future" }
  }
}

object CastTest extends App {

  // there is some 'interesting' stuff about casting and

  val l: List[Int] = List[String]("a").asInstanceOf[List[Int]]

  println(l)      // List[Int] = List(a)

  println(l.head) // a

  // --

  val client = new Client()

  val fResult = fun(new OrderB())

  val result = Await.result(fResult, 10.seconds)

  println(result)

  // --
  def fun(a:Order)(implicit executionContext: ExecutionContext) : Future[String]  = {

    val x = a.asInstanceOf[OrderC] // case cast exception

    client.send("the payload")

  }
  

}
