package collections.streams

import scala.collection.immutable.Stream.cons

// #stream
// related: #cons

// In nutshell: Streams are similar to list but a tail is evaluated only on demand

// Inspired by : http://daily-scala.blogspot.ca/2010/01/introducing-streams.html

object StreamSample extends App {

  // #1
  {
    val stream1 = Stream(1,2,3,4,5)

    // same as:

    val stream2 = (1 to 5).toStream  // any collection could be transformed to stream

    println (stream1) // Stream(1, ?)  - as you can see the tail is not evaluated yet
    println (stream2) // Stream(1, ?)  - as you can see the tail is not evaluated yet
  }

  // #2
  {

    val stream1 = Stream(1,2,100,4,5)


    val first3Values= stream1.take(3)

    println ("first3Values: " + first3Values)                 // Stream(1, ?)         Not evaluated !
    println ("first3Values.toList: " + first3Values.toList)   //  List(1, 2, 100)     Evaluated !

  }

  println ("\n#3 \n")

  // #3 cons method  - is the same is :: in lists. Same as: #:: operator (see below)
  {

    // cons takes :
    // 1. the value at the start point and
    // 2. a function to return the rest of the  stream NOT another stream.

    val stream = cons(0, cons(1, Stream.empty ))


    println ("stream: " + stream)  // stream: Stream(0, ?)

//  The accessing the second element will run the function.
// Notice it is not evaluated until request !

    val stream2 = cons (0, {
                              println("getting second element")
                              cons(1, Stream.empty)
                           }
                        )

    println ("stream2: " + stream2) // stream2: Stream(0, ?) -- NO "getting second element" appearing !

    stream2(0)  // NO "getting second element" appeared !
    stream2(1)  // this will lead to "getting second element" appearing

    // Function is only evaluated once:

    stream2(1)  // NO "getting second element" appeared !



  }

  // #4  - force method, + Stream.from method
  {

    // Create an infinite stream starting at `start` and incrementing by `1`.

    // Stream.from(start = 100).force  // leads to OutOfMemoryException

  }

  println ("\n#3 \n")

  // #5 "#::" method  - same as "Stream.cons", same as "::" in List
  {
    val stream5 = 0 #:: { println("hi"); 1 } #:: Stream.empty

    println ("stream5:" + stream5)

    stream5(1) // hi
  }

}
