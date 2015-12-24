package collections.lists

// #list #zip #index

object ZipSamples extends App {

  val list = List("a", "b", "c")

  // #1
  val zipped1 = list zip List(0,1,2)      // List[(String, Int)] = List((a,0), (b,1), (c,2))
  val zipped2 = list zip List(0,1,2,3,4)  // List[(String, Int)] = List((a,0), (b,1), (c,2)) -- same as before !

  println("zipped1: " + zipped1)
  println("zipped2: " + zipped2)

  // #2 with Index
  val zippedIWithIndex = list.zipWithIndex // List((a,0), (b,1), (c,2))

  // same as:   list zip List(0,1,2,3,4)

  println ("zippedIWithIndex:" + zippedIWithIndex)

}
