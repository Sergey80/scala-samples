package collections.lists

// #list #reduce

// Reduce - Simplified version of foldLeft (because we do not need pass seed-value, like we do in fold(seed)(fn)).

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


  // 4. empty list  - will fail

  val emptyList = List[Int]()

  //val sum4 = emptyList reduceLeft( sumOp )  // UnsupportedOperationException: empty.reduceLeft

  // putting initial value

  val sum4 = (0 :: emptyList).reduceLeft( sumOp )  // 0  -- same as #fod : foldLeft(0)(sumOp)

  println (sum4)


}
