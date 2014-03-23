package design_patterns.decorator

import design_patterns.decorator.decorators.{NuttyDecorator, HoneyDecorator}

// inspired by: http://javapapers.com/design-patterns/decorator-pattern/

// #decorator-pattern

object DecoratorSample extends App {

  // what is interesting in decorator patten in scala is that
  //  - no need to create additional class 'IcecreamDecorator' (like in classical OOP)
  //  - and no need to care about delegation calls from out IcecreamDecorator.makeIcecream() method

  // making os of 'traits' helps us avoid boilerplate code creation

  // creating an IceScream with ingredients / decorators
  val iceCream = new JustIceCream with NuttyDecorator with HoneyDecorator

  // sequence of 'with' does matter !

  println (iceCream makeIcecream) // Base IceCream + crunchy nuts + sweet honey

}
