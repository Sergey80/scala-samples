package recursion.tasks

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

  def countChange(money: Int, coins: List[Int]): Int = {

    def countInLine(line:List[Int], adding:Int): Int = {

      val newLine = line :+ adding

      if (newLine.sum < money) countInLine(newLine, adding)
        else
          if (newLine.sum == money) 1
            else 0
    }

    def countInLines(baseList:List[Int], buffer:List[Int], total:Int= 0) : Int = {

      val count = countInLine(baseList, buffer.head)

      if (buffer.tail isEmpty) total
        else
          countInLines(baseList, buffer.tail, total + count)
    }

    def countByBaseLine(initLine:List[Int], total:Int = 0): Int = {

      if (initLine isEmpty) total
        else
          countByBaseLine(initLine.tail, total + countInLines(List(initLine.head), buffer = coins))

    }

    def countByBaseLine2(initLine:List[Int], b:List[Int], total:Int = 0): Int = {

      for (c <- initLine.drop(1)) {

      }

    }


    val count = countByBaseLine(coins)

    println(count)

    0
  }


  countChange(4, List(1,2))

 /*
  assert(countChange(0,List(1,2)) == 0)

  assert(countChange(4,List()) == 0)

  assert(countChange(4,List(1,2)) == 3)

  assert(countChange(300,List(5,10,20,50,100,200,500)) == 1022)

  assert(countChange(301,List(5,10,20,50,100,200,500)) == 0)

  assert(countChange(300,List(500,5,50,100,20,200,10)) == 1022)
  */


}
