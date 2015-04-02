package fp_deep


// #functor #higher-kinded-type #yype-constructor

import scala.language.higherKinds  // if not set/imported - the warning about "higher-kinded type' will be generated

object FunctorDemo extends App {

  trait Functor[T[_]] {  // T[_] - Type Constructor that takes one type parameter. ("higher-kinded type" )
    def fmap[A, B](f: A => B): T[A] => T[B] // takes function from A=>B and returns wrapped T[A] => T[B]
  }

  val listFunctor = new Functor[List] {      // List - type constructor
    def fmap[A, B](f: A => B): List[A] => List[B] = {   // [A], [B] - type parameters for that type constructor
      case Nil     => Nil
      case a :: as => f(a) :: fmap(f)(as)   // List[B]
    }
  }

  val optionFunctor = new Functor[Option] {  // Option - type constructor
    def fmap[A, B](f: A => B): Option[A] => Option[B] = { // [A], [B] - type parameters for that type constructor
      case None    => None
      case Some(a) => Some(f(a))
    }
  }

  // just reminding:
  // case x => y    is same as (f: X => Y) - the function that takes value of type X and returns value of type Y
  // so, by these 'case's we return a function T[A] -> T[B] as was specified in trait

  val result1 = listFunctor.fmap[Int,Int]  (_ + 1) ( List(1,2,3) )    // have to specify [Int,Int] - scala/jvm limitation (comparing to Haskell)
  val result2 = optionFunctor.fmap[Int,Int](_ + 1) ( Some(1) )

  println (result1)    // List(2,3,4)
  println (result2)    // List(2)


}
