package generics

object TypeBoundsTest extends App {

  class Car {}
  class SportsCar extends Car {}

   case class Barn[A <: Animal](animals: A*) {}

   // case class Barn[Animal](animals: Animal*) {}
   //case class Barn[A](animals: A*) {}

  val animalBarn: Barn[Animal] = Barn( new Dog(), new Animal() )

  //val carBarn = Barn( new SportsCar() )  // will not let SportsCar be put

}
