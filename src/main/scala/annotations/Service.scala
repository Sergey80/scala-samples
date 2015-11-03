package annotations

// TODO: TODO

import scala.reflect.runtime.{universe => ru}

case class RoleSecured(allowed: Seq[String], denied: Seq[String]) extends scala.annotation.StaticAnnotation



class Service {

  @RoleSecured(allowed = Seq("role1"), denied = Seq("role2"))
  def doService(): String = {
    ""
  }

}

object Service extends App {

  //
  // Use reflection to extract the parameter details
  //   1. Get the ClassSymbol for the class we want to check
  //   2. Get the list of annotations from the ClassSymbol
  //   3. Get the expected annotation type to match
  //   4. Find the annotation
  //   5. Retrieve the args. These are returned as a list of Tree.

//
  //  val serviceSymbol: ru.ClassSymbol = ru.typeOf[Service].typeSymbol.asClass



//  val serviceSymbol: ru.MethodSymbol = ru.typeOf[Service].typeSymbol.asMethod
//  val serviceAnnotations = serviceSymbol.annotations
//  val roleSecuredAnnotationType = ru.typeOf[RoleSecured]

  val serviceSymbol: ru.MethodSymbol     = ru.typeOf[Service].decl(ru.TermName("doService")).asMethod
  val serviceAnnotations                 = serviceSymbol.annotations
  val roleSecuredAnnotationType: ru.Type = getTypeTag(serviceSymbol).tpe

  serviceAnnotations foreach  { x =>
    println (x.tree.tpe + " == " + roleSecuredAnnotationType)
  }

  val roleSecuredAnnotation = serviceAnnotations.find(a => a.tree.tpe == roleSecuredAnnotationType)

  val roleSecuredAnnotationArgs = roleSecuredAnnotation.get.tree.children.tail

  val a = 1

//  roleSecuredAnnotationArgs.foreach(a => println(ru.showRaw(a)))

 /*

  import scala.reflect.runtime.universe._

  def methodAnnotations[T: TypeTag]: Map[String, Map[String, Map[String, Any]]] = {
    typeOf[T].decls.collect { case m: MethodSymbol =>
      m
    }.withFilter {
      _.annotations.nonEmpty
    }.map { m =>
      m.name.toString -> m.annotations.map { a =>
        a.tree.tpe.typeSymbol.name.toString -> a.tree.children.withFilter {
          _.productPrefix eq "AssignOrNamedArg"
        }.map { tree =>
          tree.productElement(0).toString -> tree.productElement(1)
        }.toMap
      }.toMap
    }.toMap
  }

  import scala.reflect.runtime.universe
  def annotationsOf[T: universe.TypeTag](obj: T) = {
    universe.typeOf[T].members.foldLeft(Nil: List[universe.type#Annotation]) {
      (xs, x) => x.annotations ::: xs
    }
  }
*/

  def getTypeTag[T: ru.TypeTag](obj: T) = ru.typeTag[T]

/*
  val service = new Service
  val methodX = ru.typeOf[Service].decl(ru.TermName("doService")).asMethod
  val theType: ru.Type = getTypeTag(methodX).tpe
  println(theType)
*/

  //println(annotationsOf(testObj))

}
