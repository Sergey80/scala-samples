package lists

// #list

// Reduce - Simplified version of foldLeft.

// First argument is where result is passed
// Second argument is always the current item in the collection.

object ReduceExamples extends App {

  val sum1 = List(1,2,3,4,5).reduceLeft{ (total, current) => {
                                       println("total:  " + total + " current: " + current)
                                       total + current}
                                   }

  val sum2 = List(1,2,3,4,5) reduce(_ + _)  // short version


  println (sum1)  // 6 (1+2+3 = 6)

  println (sum2)  // 6


}
