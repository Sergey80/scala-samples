package fp_deep


// #functor #higher-kinded-type #type-constructor #pattern-matching #first-order-parametric-polymorphism

// special flag
import scala.language.higherKinds  // if not set/imported - the warning about "higher-kinded type' will be generated (see this paper: http://adriaanm.github.io/files/higher.pdf)

object FunctorDemo extends App {

  trait Functor[T[_]] {  // T[_] - #type-constructor that takes one type parameter. ("higher-kinded type" )
    def fmap[A, B](f: A => B): T[A] => T[B] // takes function from A=>B and returns wrapped T[A] => T[B]
  }

  /*
     Everything you need to know about #higher-kinded-type is that type may apply another type as argument (#type-constructor), and this type-argument also may have another type argument etc...
     Similarly to functions - function can takes values as arguments, where argument maybe another function that takes argument .. etc.
     Type behaves as a function.
     Usual "generics" in Java (f.e.) is "first-order parametric polymorphism" that has its limitations (because it is "first" but not _higher_ than "first")
     OOP does not exist - just in case if did not know/realize.
  */

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

// Eventually we may make this work (but not in scope of current sample):
  // List(1,2,3).fmap(_ + 1)
  // Same(1).fmap(_ + 1)
// see: 'scalaz' samples/folders

}

// NOTES:
// - " First-order parametric polymorphism like (List[T]), although it allows to abstract over types "
// - " "higher-kinded types" is generalisation to types that abstract over types that abstract over types "
