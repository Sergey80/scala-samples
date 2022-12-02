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

    groupMap.toList
  }

  val result = wordOccurrences("hello!")  // List((e,1), (!,1), (l,2), (h,1), (o,1))

  println("occurrences: " + result)

}
