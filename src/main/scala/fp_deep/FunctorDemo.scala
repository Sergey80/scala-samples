package fp_deep


//TODO: add comments

object FunctorDemo extends App {

  trait Functor[F[_]] {
    def fmap[A, B](f: A => B): F[A] => F[B]
  }

  implicit val listFunctor = new Functor[List] {
    def fmap[A, B](f: A => B): List[A] => List[B] = {
      case Nil => Nil
      case a :: as => f(a) :: fmap(f)(as)
    }
  }

  implicit val optionFunctor = new Functor[Option] {
    def fmap[A, B](f: A => B): Option[A] => Option[B] = {
      case None => None
      case Some(a) => Some(f(a))
    }
  }


  val result = listFunctor.fmap[Int,Int](_ + 1)(List(1,2,3))

  println(result)


}
