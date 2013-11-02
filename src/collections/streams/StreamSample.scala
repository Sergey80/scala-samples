package collections.streams

// #stream

// In nutshell: Streams are similar to list but a tail is evaluated only on demand

object StreamSample extends App {

  // #1
  {
    val stream1 = Stream(1,2,3,4,5)

    // same as:

    val stream2 = (1 to 5) toStream  // any collection could be transformed to stream

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

}
