package implicit_test

/**
 * In scope of implicit parameters topic (using implicit defining anonymous function as argument),
 * this example shows (roughly) how PlayFramework Action works (or might work). This might be useful to understand "how it's made"
 *
 * #implicit-parameters #implicit-arguments
 * related: #anonymous-function #currying #currying-function
 */
object ImplicitArguments extends App {

  def magic[T](implicit e: T) = e  // read about this latter in this code

  case class Request(msg:String)

  implicit val request = Request("my request") // let's say we already have a Request from the browser. So it exists/lives somewhere

  // The Action represents the Result of performing a Request (that generate that Result). Also, Result keeps the Request upon which it was made.

  case class Result(msg:String)

  case class Action(result:Result)
  object Action {
    def apply(block:Request => Result):Action = {   // note: we do NOT type/use "implicit block" here !

     //val result = block(...)                     // what value, do you thing we are going to pass here?

     // use 'implicitly()'  !
     val result1 = block(implicitly[Request])     // if case if "implicitly" sounds to you like a magic,

     val result2 = block(magic[Request])          // you can do your own "magic"

                                                  // in terms of PlayFramework, you may thing that magic() is method that
                                                    // prints result to HTML Template view
     new Action(result1)
    }
  }

  // 1. Let's test

  val action1 = Action { implicit req  =>       // passing anonymous function that use _exiting_ request implicitly as arg
    Result("Got request [" + req + "]")           //  and returns Result (result has information about the request)
  }

  // That is the same as this:
  val action2 = Action {  req =>
    implicit val r = req              // as you can see, previous form is more concise than this
    Result("Got request [" + req + "]")
  }

  println(action1) // "Action(Result(Got request [Request(my request)]))"


  // 2. What if we are not going to use _implicit_, but explicitly passing our OWN Request ?

  val myRequest = new Request("my own request")
  val action3 = Action { myRequest  =>           // why then use "implicit", because anyhow it will use Request val implicitly due to "implicitly()" method
    Result("Got request [" + myRequest + "]")    // ???  The answer is:
                                                          // - We should not use only one arg in apply() method ! It is not enough
                                                          // - We should use currying as second param - it is going to help
  }

  println(action3) // "Action(Result(Got request [Request(my request)]))"


  println("---")

 // 3. The better way to do it is to use "currying function"
 case class Action2(result:Result)
  object Action2 {
    def apply(block:Request => Result)(implicit request:Request):Action2 = { // now we use implicit !

      val result = block(request)

      // in terms of PlayFramework, you may thing that magic() is method that
      // prints result to HTML Template view
      new Action2(result)
    }
  }

  // Test it again:

  val a2_1 = Action2 { implicit req  =>       // passing anonymous function that use _exiting_ request implicitly as arg
    Result("Got request2 [" + req + "]")           //  and returns Result (result has information about the request)
  }
  println(a2_1)

  val myRequest2 = new Request("my own request")
  val a2_2 = Action2 { myRequest2  =>           // why then use "implicit", because anyhow it will use Request val implicitly due to "implicitly()" method
    Result("Got request2 [" + myRequest + "]")    // ???
  }

  println(a2_2)

}

