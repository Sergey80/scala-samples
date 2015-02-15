package implicits.usecases

object RequestResponseTest extends App {

  type User = String      // for simplicity sake - all are strings
  type Request = String
  type Result = String

  trait Action  // there are two auth Action - Ok and Not Ok
  case class OkAction(content:Result) extends Action
  case class UnauthorizedAction(content:Result) extends Action

  var userDB = List("user1", "user2", "user3") // our simple database

  val user = "user1"  // usually user available from the session
  var request:Request = "request"  // current request


  // wraps request into Action
  def withAuth(f: User => (Request => Result) ): Action = {

    println("withAuth in play...")

    val result:Result = f("user1")(request) // is user found or not

    if (result == "ok")  { println("wrapped to Action as authorized"); OkAction("") }
      else {
        println("wrapped to Action as not authorized")
        UnauthorizedAction("")
      }

  }

  //
  def withUser(f: User => (Request => Result)) : Action = {

    println("withUser in play...")

    def findInDb(user: User): Option[User] = {
      userDB.find(u => u == user)
    }

    val authResult:Action = withAuth (    // 'withUser' requires help of 'withAuth'

      // Create anonymous function to please the 'withAuth'
      // this function already knows who is user, what request is,
      // but doesn't what Result is.
      // In our case (since we are in 'withUser') the Result is the
      //  fact whether the user exists in users database.
      // So it tries to get the Result by searching for the current user.
      // If user has found:
      //  then it makes sense to continue with Authorisation (withAuth)
      // If not:
      //  then ...
      user => request => {

        val userOption = findInDb("user1") // find the user in users db

        val result:Result = userOption match {
          case Some(_) => {                         // user found
            println("user has been found");         //
            f(user)(request)                        // do work
            "ok"                                    //
          }
          case None    => {                         // user not found
            println("user has not been found"); "not ok"
          }
        }

        result // "ok" / "not ok"

      }
    )
    authResult  // OkAction / NotOkAction
  }

  // Let's run this and to some work

  def doWork() = withUser {                     // doWork -> withUser -> withAuth  (like Decorator/Wrapper pattern)
    user => request => {
      // if user exists (withUser) and authorization is ok (withAuth), then this work will done
      println("do some important work here")
      "work is done!"                           // Result is just a String
    }
  }

  val result = doWork()  // doWork doesn't care about user or request
}

// Output:
/*
withUser in play...
withAuth in play...
user has been found
do some important work here
wrapped to Action as authorized
*/