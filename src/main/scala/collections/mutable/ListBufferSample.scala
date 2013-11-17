package collections.mutable

import scala.collection.mutable.ListBuffer

// typical example where mutable List like ListBuffer might be useful

// #ListBuffer
// related: #zipWithIndex #mutable

object ListBufferSample extends App {

  case class User(id:Long, name:String, email:String)

  object Users {

    val users = ListBuffer[User]() // mutable list

    def exists(id:Long) = users.exists (_.id == id)

    def add(user:User) = {
       users += user
    }

    def update(user:User) = {

      val zipped = users.zipWithIndex   // #zipWithIndex example usage

      val optionResult = zipped find(u => u._1.id == user.id )

      if (optionResult.isDefined) {

        val ( _:User, index:Int) = optionResult.get

        users.update(index, user)

      }

    }

  }

  // test

  // add:
  Users.add( User(1:Long, "bob1", "bob1@bobs.com") )
  Users.add( User(2:Long, "bob1", "bob2@bobs.com") )

  // exists:
  println ( "user with id = 2 exists?:  " + Users.exists(2:Long) )

  println ("Users.users.size: " + Users.users.size)
  println ("All: " + Users.users)

  // update:

  Users.update( User(1, "bob1", "new-email-bob@bobs.com") ) // update User that has id = 1

  println ("All after update: " + Users.users )


}
