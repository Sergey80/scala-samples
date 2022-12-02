package collections.lists


case class Model(price: BigDecimal, source: String, isQoute: Boolean, quoteType: String )

object Sorting2 extends App {

  val all = List(
    Model(price = 1, source = "a-source1", isQoute = true, quoteType = "subject"),
    Model(price = 1, source = "b-source1", isQoute = true, quoteType = "fix"),
    Model(price = 1, source = "b-source1", isQoute = false, quoteType = "fix"),
    Model(price = 1, source = "c-source1", isQoute = false, quoteType = "."),
    Model(price = 2, source = "d-source1", isQoute = true, quoteType = "subject"),
    Model(price = 5, source = "e-source1", isQoute = false, quoteType = "subject")
  )

  val weightsQuoteTypeMap: Map[String, Int] = Map[String, Int](
    "subject" -> 0,
    "fix" -> 2
  )

  val groupedAndSortedByPrice: List[List[Model]] = all.groupBy(_.price).toList.sortWith(_._1 > _._1).map(x => x._2)

  val result = groupedAndSortedByPrice.flatMap(list =>
       list.sortWith((m1, m2) => m1.isQoute > m2.isQoute)
      .sortWith((m1, m2) => m1.source < m2.source)
      .sortWith { (m1, m2) => weightsQuoteTypeMap.getOrElse(m1.quoteType, 1) > weightsQuoteTypeMap.getOrElse(m2.quoteType, 1) })


  result foreach(println)

}