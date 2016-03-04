package generics

import generics.model.{Apple, Fruit}

object LowerTypeBoundsTest extends App {

  class Basket[T] {

    def add(x: T) = x
    def add2[S >: T](x: S) = x   // lower bounds T to S

    def addList(xs: List[T]) = xs
    def addList2[S >: T](xs: List[S]) = xs

  }

  val fruitBasket = new Basket[Apple]()

  // add

  val result1: Apple = fruitBasket.add( new Apple() )
  val result11: Fruit = fruitBasket.add( new Apple() )  // that will work too (still)

  // Apple Lower bounded to Fruit
  val result2: Fruit = fruitBasket.add2(  new Apple() )

  // addList

  val fruitList = List[Fruit](new Apple(), new Apple())

  // will not work, because "addList" expects List of Apple
//  val apples1: List[Apple] = fruitBasket.addList( fruitList )
//  val apples2: List[Fruit] = fruitBasket.addList( fruitList )

  // will work, because addList2 will bound all apples into fruits (to super class of Apple) !

  val fruits = fruitBasket.addList2( fruitList )  // returns already bounded to "List[Fruit]"
}
