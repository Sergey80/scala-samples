package akkastreams

// http://doc.akka.io/docs/akka/2.4.2/AkkaScala.pdf  (7.1 STREAMS)

// http://blog.michaelhamrah.com/2015/01/a-gentle-introduction-to-akka-streams/

/**
  * The Ingredients
  * A Source is something which produces exactly one output. If you need something that generates data, you need a Source. Our source above is produced from the connection.consume function.
  * A Sink is something with exactly one input. A Sink is the final stage of a Stream process. The .foreach call is a Sink which writes the input (_) to the console via println.
  * A Flow is something with exactly one input and one output. It allows data to flow through a function: like calling map which also returns an element on a collection. The map call above is a Flow: it consumes a Delivery message and outputs a String.
**/

import java.io.File

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.{ExecutionContext, Future}

/**
  * Akka Streams is a library to process and transfer a sequence of elements using bounded buffer space. This
latter property is what we refer to as boundedness and it is the defining feature of Akka Streams. Translated to
everyday terms it is possible to express a chain (or as we see later, graphs) of processing entities, each executing
independently (and possibly concurrently) from the others while only buffering a limited number of elements at
any given time. This property of bounded buffers is one of the differences from the actor model, where each actor
usually has an unbounded, or a bounded, but dropping mailbox. Akka Stream processing entities have bounded
“mailboxes” that do not drop.
  */

object Step1_SourceAndSinck extends App {

  implicit val system = ActorSystem("QuickStart")

  // ActorMaterializer is a factory for stream execution engines, it is the thing that makes streams run
  implicit val materializer = ActorMaterializer() // the Actor to run streams

  // --

  // The Source is just a description of what you want to run
  val source: Source[Int, NotUsed] = Source(1 to 100) //  to emit the integers 1 to 100:

  // In order to get those numbers out we have to run it:


  // 1. print Source to the console
  source.runForeach(i => println(i))(materializer)        // put it to "Sink" one by one


  // 2. to write the Source to file (transforming by reading it). The File is the Sink in that case

  val factorials: Source[BigInt, NotUsed] = source.scan(BigInt(1))((acc, next) => acc * next)  // "scan" like foldLeft ??

  val fileWritingSink = FileIO.toFile(new File("factorials.txt"))

   val result: Future[IOResult] = factorials.map(num => ByteString(s"$num\n") ). // to Source[ByteString, NotUsed]
     runWith(sink = fileWritingSink)  // sink is a file


  // IOResult is a type
  // that IO operations return in Akka Streams in order to tell you how many bytes or elements were consumed and
    // whether the stream terminated normally or exceptionally.

    val ec = ExecutionContext.global
    result.foreach{ x =>
      println(s"Count: ${x.getCount} bytes")              // will print: Count: 7093 bytes
    }(ec)

}
