package design_patterns.decorator.decorators

import design_patterns.decorator.JustIceCream

trait NuttyDecorator extends JustIceCream {
  abstract override def makeIcecream(): String = {
    super.makeIcecream + " + crunchy nuts"
  }
}
