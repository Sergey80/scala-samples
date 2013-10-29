package lists

object ReduceExamples extends App {

  val sum1 = List(1,2,3).reduceLeft{ (left, right) => {
                                       println("left:  " + left + " right: " + right)
                                       left + right}
                                   }

  val sum2 = List(1,2,3) reduce(_ + _)  // short version


  println (sum1)  // 6 (1+2+3 = 6)

  println (sum2)  // 6


}
