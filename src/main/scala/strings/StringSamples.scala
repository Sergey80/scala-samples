package strings

// #string  #string-context

object StringSamples extends App {

  // #1 just don't forgot about this.
  {
    val name = "Bob"

    val someString = s"Hello, $name!"  // this is much better that: "Hello" + name + "!"

    println (someString)

  }

}
