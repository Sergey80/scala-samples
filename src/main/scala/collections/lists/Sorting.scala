package collections.lists

// #sort Sorting by name and by position

object Sorting extends App {

  val sortByNameAndPosition = new Ordering[Person] {
    def compare(p1: Person, p2: Person) = {
      val comparedByName =p1.name.compare(p2.name)
      if (comparedByName == 0) {
        p1.position.compare(p2.position)
      } else {
        comparedByName
      }
    }
  }

  case class Person(name:String, position:String) extends Ordered [Person] {
    override def compare(that: Person): Int = {
      sortByNameAndPosition.compare(this, that)
    }
  }


  val persons = Seq( Person("bob", "b"), Person("bob", "a"), Person("anh", "a") )

  // 1.
  val result1 = persons.sorted // / List(Person(anh,a), Person(bob,a), Person(bob,b))

  // 2.
  val result2 = persons.sorted(new Ordering[Person] {
      override def compare(x: Person, y: Person): Int = {
        x.name.compare(y.name) // only by name
      }
    })

  // 3.
  val result3 = persons.sortWith(_.name < _.name) // same as 2, but seems less verbose and less 'centric'

  val result4 = persons.sortBy(_.name)            // same as 2 and 3, but maybe less specif/clear about order direction

  val result5 = persons.sortBy(_.name)            // same as 2 and 3, but maybe less specif/clear about order direction

  val result6 = persons.sortBy( p => (p.name, p.position) ) // same as 1 but much less verbose and less 'centric' but less specific about sorting direction

  val result7 = persons.sorted(sortByNameAndPosition) // same as 1 but more specific (due the passed named function)

  val result8 = persons.groupBy(_.name).toList.
                        sortWith(_._1 < _._1/*name*/).
                        flatMap(_._2/*position*/.sortWith(_.position < _.position))  // same as 1. a little bit verbose, but step-by-step-clear and customizable

  println("1." + result1) // List(Person(anh,a), Person(bob,a), Person(bob,b))
  println("2." + result2) // List(Person(anh,a), Person(bob,b), Person(bob,a))
  println("3." + result3) // List(Person(anh,a), Person(bob,b), Person(bob,a))
  println("4." + result4) // List(Person(anh,a), Person(bob,b), Person(bob,a))
  println("5." + result5) // List(Person(anh,a), Person(bob,b), Person(bob,a))
  println("6." + result6) // List(Person(anh,a), Person(bob,b), Person(bob,a))  // same as 1.
  println("7." + result7) // List(Person(anh,a), Person(bob,b), Person(bob,a))  // same as 1.
  println("8." + result8) // List(Person(anh,a), Person(bob,a), Person(bob,b))  // same as 1

}
