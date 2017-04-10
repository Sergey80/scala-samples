package lenses

// Sample a problem, that we would solve with either Moncole, Shapless or ScalaZ ' lenses.

object NoLensesSample extends App {

  import CommonData.theEmployee // look there fo the detail of that object

    // The problem is here:
    val newEmployee = theEmployee.copy(
      company = theEmployee.company.copy(
        address = theEmployee.company.address.copy(
          street = theEmployee.company.address.street.copy(
            name = theEmployee.company.address.street.name.capitalize // capitalize !
          )
        )
      )
    )
    // too much of code !

    println(newEmployee) // ... ,High street

}
