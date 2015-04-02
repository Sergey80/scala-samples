package fp_deep


// #functor #higher-kinded-type #type-constructor #pattern-matching

// special flag
import scala.language.higherKinds  // if not set/imported - the warning about "higher-kinded type' will be generated

object FunctorDemo extends App {

  trait Functor[T[_]] {  // T[_] - #type-constructor that takes one type parameter. ("higher-kinded type" )
    def fmap[A, B](f: A => B): T[A] => T[B] // takes function from A=>B and returns wrapped T[A] => T[B]
  }

  // Everything you need to know about #higher-kinded-type is that
  // type may apply another type as argument (#type-constructor),
  // similarly to functions - function can takes values as arguments
  // type behave as a function
  // OOP does not exist - just in case if did not know/realize. There is no spoon :)

  // let's define/implement two Functors then

  val listFunctor = new Functor[List] {      // List - type constructor
    def fmap[A, B](f: A => B): List[A] => List[B] = {   // [A], [B] - type parameters for that type constructor
      case Nil     => Nil
      case a :: as => f(a) :: fmap(f)(as)   // List[B]
    }
  }

  // same as 'map' but 'map' works only with List-like types (not as abstract as Functor's fmap where we pass a type)

  val optionFunctor = new Functor[Option] {  // Option - type constructor
    def fmap[A, B](f: A => B): Option[A] => Option[B] = { // [A], [B] - type parameters for that type constructor
      case None    => None
      case Some(a) => Some(f(a))
    }
  }

  // just reminding:
  // case x => y    is same as (f: X => Y) - the function that takes value of type X and returns value of type Y

  val result1 = listFunctor.fmap[Int,Int]  (_ + 1) ( List(1,2,3) )    // have to specify [Int,Int] - scala/jvm limitation (comparing to Haskell)
  val result2 = optionFunctor.fmap[Int,Int](_ + 1) ( Some(1) )

  println (result1)    // List(2,3,4)
  println (result2)    // List(2)

// -------------- code reminding about #pattern-matching:

  val map = Map[Int,String] (1->"A", 2->"B")
  map foreach {
      case(k, v) if k==1 => println("yes, we can decide which function to apply")
      case(k,v) => println("k = " + k + "; v = " + v)
  }
  // 1. "yes, we can decide which function to apply"
  // 2. "k = 2; v = B"


}
