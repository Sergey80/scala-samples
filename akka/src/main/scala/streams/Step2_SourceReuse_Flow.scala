package streams

import java.io.File

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.{ExecutionContext, Future}

// shows that we can reuse Sinks
// shows how to use Flow

object Step2_SourceReuse_Flow extends App {

  implicit val system = ActorSystem("QuickStart")

  // ActorMaterializer is a factory for stream execution engines, it is the thing that makes streams run
  implicit val materializer = ActorMaterializer() // the Actor to run streams

  //
  //val ec = ExecutionContext.global


  //converts sink of ByteStrings to Sink of Stings. Uses the Flow
  def lineSink(sink:Sink[ByteString, Future[IOResult]]): Sink[String, Future[IOResult]] =
    Flow[String] // #Flow of strings.
      .map(str => ByteString(str + "\n")) // converts each str to ByteString
      .toMat(sink)(Keep.right) // then feed to the file-writing Sink

  // --

  val numSource: Source[Int, NotUsed] = Source(1 to 100) //  to emit the integers 1 to 100:
  val fileWritingSink: Sink[ByteString, Future[IOResult]] = FileIO.toFile(new File("factorial2.txt"))

  // converts source of number to the source of factorials of each number from those numbers
  val factorialsSource: Source[BigInt, NotUsed] = numSource.scan(BigInt(1))((acc, next) => acc * next)  // "scan" like foldLeft ??

  // converts to file Sink into Sink of strings
  val sinkOfStrings: Sink[String, Future[IOResult]] = lineSink(sink = fileWritingSink)

  val ioResult: Future[IOResult] = factorialsSource.map(num => num.toString).runWith(sinkOfStrings)(materializer)
}
