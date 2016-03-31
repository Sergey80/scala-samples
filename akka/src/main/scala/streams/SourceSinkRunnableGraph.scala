package streams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, RunnableGraph, Sink, Source}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/*

Flow - A processing stage which has exactly one input and output,
which connects its up- and downstreams by transforming the data elements flowing through it.

RunnableGraph - A Flow that has both ends “attached” to a Source and Sink respectively,
and is ready to be run().
*/

object SourceSinkRunnableGraph extends App {

  implicit val system = ActorSystem("QuickStart") // the Actor to run streams
  implicit val materializer = ActorMaterializer() // a factory for stream execution engines, it is the thing that makes streams run

  // --

  val source = Source(1 to 10)
  val sink: Sink[Int, Future[Int]] = Sink.fold[Int, Int](0)(_ + _)

  // connect the Source to the Sink, obtaining a RunnableGraph
  val runnableGraph: RunnableGraph[Future[Int]] = source.toMat(sink)(Keep.right)

  // Materialization - is the process of allocating all resources needed to run the computation described by a Graph

  val futureResult: Future[Int] = runnableGraph.run() // running = materializing
                                                      // get back the materialized value of type T of RunnableGraph[T].

  val result = Await.result(futureResult, 10.seconds) //

  println(result)
}
