package extractors

/*
 * In nutshell: If apply() method usually creates an object from/by inputs parts, as factory method,
 *
 * Then unapply method - extracts those parts from the object in favour of / (To be used in) pattern-matching
 *
 * that's why it is called "Extractor"
 *
 * #extractor, unapply-method
 * related: #companion-object #pattern-matching #variable-binding related
*/
object ExtractorsTest extends App {

  // let's say we have class email
  class Email {
    var name:String = ""
    var host:String = ""
  }

  // an companion-object that contains extractor (unapply-method)
  object Email {

    def apply(str:String): Email = { // it creates new Email by String
      val email = new Email()

      val ar = str.split("@")  // converts string to the object
      email.name = ar(0)
      email.host = ar(1)

      email
    }

    // extractor !
    def unapply(email:Email) : Option[(String, String)] = { // extractor splits object apart
      Some(email.name, email.host)              // to be able match it by name & domain
    }

  }

  // usage:
  val email = Email("bob@gmail.com")


  // 1. #pattern-matching related

  email match {
    case Email(name, domain) => println("name: " + name, "domain:" + domain)  // (name: bob,domain:gmail.com)
    case _ => println("no")
  }

  // when we do email 'match ..' it calls 'Email.unapply(email)' and returns 'Some("bob","gmail")'  !



  // 2. Let's make extractor to help us ever more, filtering the expecting result by pattern-matching. (#pattern-matching related)
  object Domain {

    // extractor !
    def unapply(host: String): Option[(String)] = {

       val domains = host.split("\\.")  // splitting by dot ( "." )

       if (domains.length > 1) return Some(domains(1))  // for now we care only about 1-th domain to keep things simple

       None

    }
  } // as you can see to keep things simple we even do not create apply() method, and there is no class Domain, only object is used

  email match {

    // this part is interesting
    case Email(name, host @ Domain("com")) => println ("yes, this is .com domain")  // Domain.unapply(host) !

    // what do you think "host" is here ? it has the following value: "gmail.com"

    // what this about? we binds (@ means 'to bind to') host to Domain.  (#variable-binding related)

    // and what does it mean to bind ?? It means to pass it to extractor (to unapply method) - Domain.unapply(host)

    // there is no magic, this just a syntax, we should remember: if inside "case" - that's all about unapply()

    case _ => println("nothing has been matched")
  }

}
