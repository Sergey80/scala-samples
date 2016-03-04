package generics

// #lower-type-bounds

class CovariantBasket[+T] { // support Covariance - f.e.: consider Basket[Fruit] a superType of Basket[Apple]

//def add(x: T): T = {x} // will not compile when +T / covariance is on

  // add2' x:S is slightly less restrictive type:
  // While upper type bounds limit a type to a subtype of another type,
  // lower type bounds declare a type to be a supertype of another type

  def add2[S >: T](x: S): S = { // lower bounds S to super class T
    x
  }

}



