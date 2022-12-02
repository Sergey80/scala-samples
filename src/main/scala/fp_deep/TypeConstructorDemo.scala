package fp_deep

// should be read before FunctorDemo

import scala.language.higherKinds  // if not set/imported - the warning about "higher-kinded type' will be generated (see this paper: http://adriaanm.github.io/files/higher.pdf)

// #type-constructor #higher-kinded-type

// trait Base[T, Container[XYZ]] ...
// trait Base[T, Container[Any]] ...   Any is not treated as Any-trait here but just name of imaginary type, like T, X or whatever
// trait Base[T, Container[_]] ...
// trait Base[T, Container]         - will not work

trait Base[T, Container[X]] {     // Container here represents a Type that may take another type as argument. Like: List[String] where List is type and String is argument
  def printContainer(p: Container[T]) = println(p)  // here we abstract over type "Container" over type "T"
}

trait Child[T] extends Base[T, List]


object TypeConstructor extends App {

  val child = new Child[String](){}

  child.printContainer( List[String]("1", "2", "3") )   // List(1, 2, 3)

  //child.printContainer( List(1,2,3))   // excepts to be List[String]
}

/* NOTE:
 - we do not use "X" anywhere. It is here to say just that fact that Container has to be type constructor - to be type-configurable
 - so instead putting X here we may put "Any" or "_"  -  Base[T, Container[Any]], Base[T, Container[_]]
 - to be able "to abstract over type over type" is "higher-kinded type"-ing
*/
