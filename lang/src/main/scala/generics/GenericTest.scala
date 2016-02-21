package generics

// variance annotations

 class Person(val name: String) {
  override def toString = name
 }

 class Employee(override val name: String) extends Person(name)


object GenericTest extends App {

  val empList     : List[Employee] = List(new Employee("Joao"), new Employee("Andre"))

  val peopleList  : List[Person]    = List(new Person("Martin"), new Person("Jonas"))

  // def sayHi(people:List[Employee]) = people.map { println _ }

  //def sayHi[T <: Person](people: List[T]) = people.map { println _ }                          // Covariance
  def sayHi[T <: Person](people: List[T]) = people.map { println _ }                          // Covariance

  sayHi(empList)
  sayHi(peopleList) // compilation ERROR

  // The error is pretty obvious we can’t send a List[Person] as the sayHi function expects a List[Employee] as it’s formal parameter, and here is where covariancecomes into place.

  // --

  // T sets the lower level in the Type hierarchy for type D.

  def appendToPerson[T, D >: T](employees: List[T], people: List[D]) = people ++ employees   // Contravariance

  val result = appendToPerson(empList, peopleList)

  println(result)

  // --
  /** *
    * A class C[T], when a method accepts only C[T] is invariant
    * A class C[+T], when a method accepts  C[T] can also get any of it’s derivatives
    * A class C[+-T], when a method accepts C[T] can also get any of it’s super classes
  */
}
