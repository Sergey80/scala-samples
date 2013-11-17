package collections.mutable

import scala.collection.mutable.ListBuffer

// typical example where mutable List like ListBuffer might be useful

// #ListBuffer
// related: #zipWithIndex #mutable

object ListBuffer_CRUD_Sample extends App {

  case class User(id:Long, name:String, email:String)

  object Users {

    val users = ListBuffer[User]() // mutable list

    def exists(id:Long) = users.exists (_.id == id)

    def add(user:User) = {
       users += user
    }

    def update(user:User) = {

      val zipped = users.zipWithIndex   // #zipWithIndex example usage

      val optionResult = zipped find(u => u._1.id == user.id ) // then find user by ID

      if (optionResult.isDefined) {

        val ( _:User, index:Int) = optionResult.get

        users.update(index, user)                  // update expects Index

      }

    }

    def delete(user:User) = {  // delete by object ref / instance

      val zipped = users.zipWithIndex   // #zipWithIndex example usage

      val optionResult = zipped find(_._1 == user) // then find user by ID

      if (optionResult.isDefined) {

        val (_:User, index) = optionResult.get

        users.remove(index)                       // remove() expects Index

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

  // remove:

  Users.delete( User(1, "bob1", "new-email-bob@bobs.com") )  // bob1 will be deleted

  println ("All after delete: " + Users.users )


}
