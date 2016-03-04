package generics

import generics.model.{Apple, Fruit}

object InvariantBasketTest extends App {

  val b1:InvariantBasket[Fruit] = new InvariantBasket[Fruit]

  // InvariantBasket is invariant, so it will not compile
   // - it will not treat InvariantBasket[Fruit] as super type of InvariantBasket[Apple]

  //val b2:InvariantBasket[Fruit] = new InvariantBasket[Apple]


}
