package generics

class Animal {}
class Dog extends Animal {}

object CovarianceTest extends App {
  
// variance:

  case class Data[+B](elements: B*) {} // simplification; covariance like in original List

  // If a function expects a List[Animal] parameter you can also pass a List[Dog]
  // as an argument to the function instead. List[Dog] is considered a subclass
  // of List[Animal] due to the covariance of List.
  // It would not work if List was invariant.

  case class Shelter(animals: Data[Animal]) {}

  val animalShelter : Shelter = Shelter( Data[Animal] (new Animal()) )
  val dogShelter    : Shelter = Shelter( Data[Dog]    (new Dog())    )  // would not work without +B

}
