// #implicitly #implicit

package implicits

/**
 * In scope of implicit parameters topic (using implicit defining anonymous function as argument),
 * this example shows (roughly) how PlayFramework Action works (or might work). This might be useful to understand "how it's made"
 *
 * #implicit-parameters #implicit-arguments
 * related: #anonymous-function #currying #currying-function
 */
object ImplicitArguments extends App {

  def inject[T](implicit e: T) = e  // read about this latter in this code

  case class Request(msg:String)

  implicit val request = Request("my request") // let's say we already have a Request from the browser. So it exists/lives somewhere

  // The Action represents the Result of performing a Request (that generate that Result). Also, Result keeps the Request upon which it was made.

  case class Result(msg:String)

  case class Action(result:Result)

  // companion object
  object Action {

    def apply(block : Request => Result) : Action = {

      // use 'implicitly()'  !
      val result1 = block(implicitly[Request])     // injecting request value by type Request

      val result2 = block(inject[Request])          // we can call it 'inject' if you like

      new Action(result1)
    }

  }
  // Ok, remember - Action is trying to inject Request value implicitly !!!


  // 1. Let's test

  // passing anonymous function that use _exiting_ request implicitly as arg
  val action1 = Action { implicit req  =>   // creating and passing anonymous function (that takes request and returns Result)
    Result("Got request [" + req + "]")     //  and returns Result (result has information about the request)
  }

  // Actually that is the same as this:
  val action2 = Action {  req =>  // note no any 'req' variable was defined above !

    implicit val r :Request = req              // implicit was put here

    // Reminding - Action will try to inject Request implicitly by type.
    // So here it was

    Result("Got request [" + req + "]") // <- this apply function will search for implicit
  }

  println(action1) // "Action(Result(Got request [Request(my request)]))"
  println(action2) // "Action(Result(Got request [Request(my request)]))"

// --

  // Let's consider advantages

  // 2. What if we are not going to use _implicit_, but explicitly passing our OWN Request ?

  val myRequest = new Request("my own request")
  val action3 = Action { myRequest  =>           // why then use "implicit", because anyhow it will use Request val implicitly due to "implicitly()" method

    // no any implicit were used

    Result("Got request [" + myRequest + "]")    // ???  The answer is:
                                                          // - We should not use only one arg in apply() method ! It is not enough
                                                          // - We should use currying as second param - it is going to help
  }

  println(action3) // "Action(Result(Got request [Request(my request)]))"


  println("---")

 // 3. The better way to do it is to use "currying function"
 case class Action2(result:Result)
  object Action2 {
    def apply(block:Request => Result)(implicit request:Request) : Action2 = { // now we use implicit !

      val result = block(request)

      // in terms of PlayFramework, you may thing that magic() is method that
      // prints result to HTML Template view
      new Action2(result)
    }
  }

  // Test it again:

  val a2_1 = Action2 { req  =>       // passing anonymous function that use _exiting_ request implicitly as arg
    Result("Got request2 [" + req + "]")           //  and returns Result (result has information about the request)
  }
  println(a2_1)               // Action2(Result(Got request2 [Request(my request)]))

  val myRequest2 = new Request("my own request")
  val a2_2 = Action2 { myRequest2  =>
    Result("Got request2 [" + myRequest + "]")
  }

  println(a2_2)               // Action2(Result(Got request2 [Request(my own request)]))    ( Now it is Ok.!)

}

