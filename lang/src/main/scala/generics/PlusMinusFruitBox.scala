package generics

// #covariance, #contravariance

class BoxPlus[+A]   // covariance

class BoxMinus[-A]  // contravariant

trait Thing
trait Fruit extends Thing
class Orange extends Fruit
class Apple extends Fruit
class BigApple extends Apple

object PlusMinusFruitBox extends App {

  def fooFruitPlus(x: BoxPlus[Fruit])    : BoxPlus[Fruit] = identity(x)
  def fooFruitMinus(x: BoxMinus[Fruit])  : BoxMinus[Fruit] = identity(x)

   fooFruitPlus( new BoxPlus[Apple])    // Apple >= Fruit          // works only with "+"
   fooFruitPlus( new BoxPlus[Fruit])    // Fruit >= Fruit
// fooFruitPlus( new BoxPlus[Thing])    // Thing is not >= Fruit
   fooFruitPlus( new BoxPlus[BigApple]) // BigApple >= Fruit       // works only with "+"

   fooFruitMinus( new BoxMinus[Fruit] ) // Fruit <= Fruit  (less or equal)
   fooFruitMinus( new BoxMinus[Thing] ) // Thing <= Fruit          // works only with "-"
// fooFruitMinus( new BoxMinus[Apple])  // Apple is not <= Fruit

}

