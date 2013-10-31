package lists

// #list

// Reduce - Simplified version of foldLeft.

// First argument is where result is passed
// Second argument is always the current item in the collection.

object ReduceExamples extends App {

  val theList = List(1,2,3,4,5)

  // 1.
  val sum1 = theList reduceLeft{ (total, current) => {
                                       println("total:  " + total + " current: " + current)
                                       total + current}
                                   }

  // 2.
  val sum2 = theList reduceLeft(_ + _)  // short version

  // 3.

  def sumOp(total: Int, current:Int) = total + current

  val sum3  = theList reduceLeft( sumOp )


  println ("sum1: " + sum1)  // 15

  println ("sum2: " + sum2)  // 15

  println ("sum3:"  + sum3)  // 15


}
