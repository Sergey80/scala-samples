package concurrency.apptmpl.managers

import concurrency.apptmpl.services.{Services, Service}
import scala.concurrent.Future

object Manager1 {

  //implicit val ex2 = Managers.ex2

  val service1 = new Service()

  def doManagerStuff1(): Future[String] = { // 2. no execution context passed

        println("did manager stuff on " + Thread.currentThread().getName)

        val serviceResult = service1.doServiceStuff("service1")

        serviceResult.map{result =>
          "did manager stuff " + result
        }(Services.blockingExContext) // !
  }

  /*
   *  Demonstrates a problem
  */
  def doManagerStuff2(): Future[String] = {
    // no execution context has been passed as arg

    println("did manager stuff on " + Thread.currentThread().getName)

    import Managers.waitingCtx // 1 fixed threads - used in 'map' to wait

    val ss = (1 to 10000) map {n =>
      service1.doServiceStuff(s"service ${n}") map {s =>
        service1.doServiceStuff(s"service2 ${s}")
      }
    }

    Future.sequence(ss).map{x =>
    "www"
    }

//    val stuffF1 = service1.doServiceStuff("service 1")
//    val stuffF2 = service1.doServiceStuff("service 2")
//    val stuffF3 = service1.doServiceStuff("service 3")
//    val stuffF4 = service1.doServiceStuff("service 4")
//    val stuffF5 = service1.doServiceStuff("service 5")

//    stuffF1.flatMap { s1 =>
//     stuffF2.flatMap { s2 =>
//      stuffF3.flatMap { s3 =>
//        stuffF4.flatMap { s4 =>
//          stuffF5.map { s5 =>
//            s1 + ", " + s2 + ", " + s3 + ", " + s3 + ", " + s5
//          }
//        }
//      }
//     }
//    }

// same as:

//    val stuffResult: Future[String] = for {
//       stuff1 <- stuffF1
//       stuff2 <- stuffF2
//       stuff3 <- stuffF3
//       stuff4 <- stuffF4
//       stuff5 <- stuffF5 // will create new thread in Services.ex
//    } yield stuff1 + ", " + stuff2 + ", " + stuff3 + ", " + stuff4 + ", " + stuffF5
//
//    stuffResult

  }

}

