package design_patterns.decorator

// inspired by: http://javapapers.com/design-patterns/decorator-pattern/

// #decorator-pattern #traits

object DecoratorSample extends App {

  // creating an IceScream with ingredients / decorators
  val iceCream = new JustIceCream with NuttyDecorator with HoneyDecorator

  // sequence of 'with' does matter !

  println (iceCream.makeIcecream()) // Base IceCream + crunchy nuts + sweet honey

}

trait IceCream {
  def makeIcecream(): String
}

class JustIceCream extends IceCream {
  def makeIcecream(): String = {
    "Base IceCream"
  }
}

// decorators

// what is interesting in decorator patten in scala is that
//  - no need to create additional class 'IcecreamDecorator' (like in classical OOP)
//  - and no need to care about delegation calls from out IcecreamDecorator.makeIcecream() method

// making use of 'traits' helps us avoid boilerplate code creation

trait HoneyDecorator extends JustIceCream {
  abstract override def makeIcecream(): String = {
    super.makeIcecream + " + sweet honey"
  }
}

trait NuttyDecorator extends JustIceCream {
  abstract override def makeIcecream(): String = {
    super.makeIcecream + " + crunchy nuts"
  }
}
