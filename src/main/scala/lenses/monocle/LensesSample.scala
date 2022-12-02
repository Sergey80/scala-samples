package lenses.monocle

// http://julien-truffaut.github.io/Monocle/

import lenses.{Address, Company, Employee, Street}
import monocle.{Lens, PLens}
import monocle.macros.GenLens

object LensesSample extends App {

 import lenses.CommonData.theEmployee

  println(theEmployee)

  {

    // we have to create "lenses" for each employee' field.
    // So then we can move/hide is somewhere (as configuration details)
    val company: Lens[Employee, Company] = GenLens[Employee](_.company) // Company of Employee
    val address: Lens[Company, Address] = GenLens[Company](_.address)   // Address of Company
    val street: Lens[Address, Street] = GenLens[Address](_.street)      // Street of Address
    val streetName: Lens[Street, String] = GenLens[Street](_.name)      // String's street name of Street

    // Now, "zoom in" to the street name
    val streetNameLense = company composeLens address composeLens street composeLens streetName

    // we can read it as: for theCompany field, modify by applying capitalize-function the employee object
    val newEmployee1 = streetNameLense.modify(_.capitalize)(theEmployee) // much simple - one line of code

    import monocle.function.Cons.headOption // to able to zoom in more precisely - sort of partial lense
    val newEmployee2 = streetNameLense.composeOptional(headOption).modify(_.toUpper)(theEmployee) // much simple - one line of code

    // How does it know that capitalize should be applied fro the Street name ?

    println(newEmployee1) // ~ ... ,High street
    println(newEmployee2) // ~ ... ,High street
  }

  // with macro it is even simpler

  {
    import monocle.function.Cons.headOption // to able to zoom in more precisely
    import monocle.macros.syntax.lens._ // macro !

    // use lens-macro - very clear now !

    val streetNameLense = theEmployee.lens(_.company.address.street.name).composeOptional(headOption)

    val newEmployee = streetNameLense.modify(_.toUpper)

    println(newEmployee) // ~ ... ,High street
  }


}

