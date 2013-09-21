package returns

object Returns3 extends App {

  def foo(): Int = {

    {
      return 1   // returns ouf ot the function "foo()"
    }

    return 2
  }

  println(foo())

}
