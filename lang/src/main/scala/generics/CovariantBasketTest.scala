package generics

// #lower-type-bounds

import generics.model.{Orange, Fruit, Apple}

// class CovariantBasket[+T]

object CovariantBasketTest extends App {

  val q1 = new CovariantBasket[Apple]

  // def add2[S >: T](x: S): S = { // lower bound S to super class T

  val a: Fruit =  q1.add2(new Orange) // Orange is lower bounded to Fruit
  val a2: Fruit = q1.add2(new Apple)      // - so we can put Orange to Apple Basket

  // --

  val q2 = new CovariantBasket[Fruit]

  val b1: Fruit =  q2.add2(new Orange) // Orange is lower bounded to Fruit
  val b2: Fruit = q2.add2(new Apple)

  // --

  val basketOfFruit: CovariantBasket[Fruit] = new CovariantBasket[Apple]() // will not work without +T defined

}
