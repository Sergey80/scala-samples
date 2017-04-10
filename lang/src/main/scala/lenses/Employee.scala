package lenses

case class Street(number: Int, name: String)
case class Address(city: String, street: Street)
case class Company(name: String, address: Address)

case class Employee(name: String, company: Company) // Aggregate Root (if you will)
