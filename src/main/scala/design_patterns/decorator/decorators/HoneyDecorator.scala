package design_patterns.decorator.decorators

import design_patterns.decorator.JustIceCream

trait HoneyDecorator extends JustIceCream {
  abstract override def makeIcecream(): String = {
    super.makeIcecream + " + sweet honey"
  }
}
