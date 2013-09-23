package recursion.tasks

import scala.collection.mutable.ListBuffer

/*
  * Write a recursive function that counts how many different ways you can make change for an amount,
   * given a list of coin denominations.
   *
   * For example, there are 3 ways to give change for 4 if you have
   * coins with denomination 1 and 2: 1+1+1+1, 1+1+2, 2+2.

  * Do this exercise by implementing the countChange() function in Main.scala.
  * This function takes an amount to change, and a list of unique denominations for the coins.
  * Its signature is as follows:

  * def countChange(money: Int, coins: List[Int]): Int

   * Once again, you can make use of functions isEmpty, head and tail on the list of integers coins.

   * Hint: Think of the degenerate cases.
   * How many ways can you give change for 0 CHF? How many ways can you give change for >0 CHF, if you have no coins?
  */
object CountChange extends App {

  // 5 => 1,2,3
  // 1

    // all variants with "2 length/LEVEL"

    // 1 1
    // 1 2
    // 1 3

    // 2 1
    // 2 2
    // 2 3

   // all variant with "3 length"

   // 1 1 ->
     // 1 1 1
     // 1 1 2
     // 1 1 3

  // 1 2 ->
    // 1 2 1
    // 1 2 2
    // 1 2 3

  // 1 3 ->
    // 1 3 1
    // 1 3 2
    // 1 3 3

  //...

  def countChange(money: Int, coins: List[Int]): Int = {

       def applyBuffer(list:List[Int], buffer:List[Int], count:Int = 0) : Int = {
         if (buffer isEmpty) count
         else
         if ( (list :+ buffer.head).max == money) applyBuffer(list, buffer.tail, count + 1)
          else applyBuffer(list, buffer.tail, count + 1)
       }

    //
    var result = 0


    result
  }


//  assert(countChange(0,List(1,2)) == 0)
//
//  assert(countChange(4,List()) == 0)

//  assert(countChange(4,List(1,2)) == 3)
//
//  assert(countChange(300,List(5,10,20,50,100,200,500)) == 1022)
//
//  assert(countChange(301,List(5,10,20,50,100,200,500)) == 0)
//
//  assert(countChange(300,List(500,5,50,100,20,200,10)) == 1022)


}
