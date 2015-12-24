package partially_applied_functions

/*
   #partially-applied-function
   related: #currying
*/

/*

Q1: So, what are the differences exactly between partially applied functions and curried functions in Scala?﻿

A: Short answer: Partially applied function (PAF) & Curried don't conflict with each others.
   Partially applied function is a tool to make General FUnction or Currying function to be partial.

*/

object PartialAppliedFunction_And_Currying extends App {


//  Partially applied function (PAF) & Curried don't conflict with each others.
//  PAF could be used for general functions as well for "currying-functions".

    def f1(x:Int, y:Int) = {x+y}
    def f2(x:Int)(y:Int) = {x+y}  // #currying related

//  So, "partially applied function" is just not-yet fully evaluated function.
//  We could convert "general function" or ether "currying function" to partially applied (partially evaluated)

// to making them partial differs in syntax
    def ff1 = f1(1, _:Int)  // "_" - specifies/represents "any value for Int type"
    def ff2 = f2(1)_  // "_" - specifies/represents "any function", - we don't know what it will return us

    println(ff1)
    println(ff2)

    println(ff1(2))
    println(ff2(2))

/*
  Output:
  <function1>
  <function1>
  3
  3
*/

}
