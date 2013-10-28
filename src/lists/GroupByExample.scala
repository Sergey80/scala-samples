package lists

object GroupByExample extends App {

  type Word = String

  type Occurrences = List[(Char, Int)]

  def wordOccurrences(word:Word):Occurrences = {


    val groupMap = word.toLowerCase groupBy {  // Map(e -> e, ! -> !, l -> ll, h -> h, o -> o)
      case ch => ch
    } map {
      case (k, chars) => (k, chars.length)   // converting
    }

//    println(groupMap)
//    println(occurrences)

    groupMap toList
  }

  val result = wordOccurrences("hello!")

  println("occurrences: " + result)

}
