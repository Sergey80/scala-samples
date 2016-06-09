package lists

// #list #group-by

object GroupByExample extends App {

  type Word = String

  type Occurrences = List[(Char, Int)]

  def wordOccurrences(word:Word):Occurrences = {


    val groupMap = word groupBy {
      case ch => ch
    } map {   // Map(e -> e, ! -> !, l -> ll, h -> h, o -> o)
      case (k, chars) => (k, chars.length)   // Map(e -> 1, ! -> 1, l -> 2, h -> 1, o -> 1)
    }

    groupMap toList
  }

  val result = wordOccurrences("hello!")  // List((e,1), (!,1), (l,2), (h,1), (o,1))

  println("occurrences: " + result)

}

/*
  val all = Seq(
              Model(1, "big"),
              Model(2, "small"),
              Model(1, "middle"),
              Model(1, "small")
            )

  val sorted: List[Model] = all.map { model =>
    model.sourceId match {
      case small@"small" => (model, 0)
      case middle@"middle" => (model, 1)
      case big@"big" => (model, 2)
    }
  }.groupBy(_._1.price).toList.sortBy(_._1).flatMap {
    case (price: BigDecimal, list: Seq[(Model, Int)]) => list.sortBy(_._2).map(_._1)
  }
  println(sorted)
*/


