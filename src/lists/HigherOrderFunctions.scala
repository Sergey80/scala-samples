package lists

object HigherOrderFunctions extends App {


// map ----------

  def map_example() = {

    val list1 = List(1,2,3)

    val result = list1.map(x=> x*2)

    println("map: " + result)

  }

  map_example()

// TODO: -----------


}
