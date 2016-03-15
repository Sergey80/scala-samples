package streams

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.{ThrottleMode, ActorMaterializer}
import akka.stream.scaladsl.Source

import scala.concurrent.duration._

import scala.concurrent.Future

// Shows how to slow down the stream to certain speed

object Step3_Timing extends App {

  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer() // the Actor to run streams
  val numSource: Source[Int, NotUsed] = Source(1 to 100) //  to emit the integers 1 to 100:

  val factorialsSource: Source[BigInt, NotUsed] = numSource.scan(BigInt(1))((acc, next) => acc * next)  // "scan" like foldLeft ??

  val done: Future[Done] =
    factorialsSource
      .zipWith(Source(0 to 100))((num, idx) => s"$idx: $num")
      .throttle(elements = 1, per = 1.second, maximumBurst=1, ThrottleMode.shaping) // slow down the stream to 1 element per second
      .runForeach(println)

  // if you try and set the streams to produce a billion numbers each then you will notice
  // that your JVM does NOT crash with an OutOfMemoryError

  // all combinators respect back-pressure - when the incoming rate is higher than one per second the
  // throttle combinator will assert back-pressure upstream.
}
