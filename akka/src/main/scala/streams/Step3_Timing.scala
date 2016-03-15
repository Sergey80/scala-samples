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

/** (from the doc:)

Some basic terminology:

_Stream_ - An active process that involves moving and transforming data.

_Element_ - An element is the processing unit of streams.
All operations transform and transfer elements from upstream to downstream.
Buffer sizes are always expressed as number of elements independently form the actual size of the elements.

_Back-pressure_ - A means of flow-control, a way for consumers of data to notify a producer about their current
availability, effectively slowing down the upstream producer to match their consumption speeds. In the
context of Akka Streams back-pressure is always understood as non-blocking and asynchronous.
Non-Blocking Means that a certain operation does not hinder the progress of the calling thread, even if it takes
long time to finish the requested operation.

_Graph_ - A description of a stream processing topology, defining the pathways through which elements shall flow
when the stream is running.

_Processing Stage_ The common name for all building blocks that build up a Graph. Examples of a processing
stage would be operations like map(), filter(), custom GraphStage s and graph junctions like
Merge or Broadcast. For the full list of built-in processing stages see Overview of built-in stages and
their semantics

When we talk about asynchronous, non-blocking backpressure we mean that the processing stages available in
Akka Streams will not use blocking calls but asynchronous message passing to exchange messages between each
other, and they will use asynchronous means to slow down a fast producer, without blocking its thread.

This is a _thread-pool friendly design_, since entities that need to wait (a fast producer waiting on a slow consumer) will not
block the thread but can hand it back for further use to an underlying thread-pool.
  */
