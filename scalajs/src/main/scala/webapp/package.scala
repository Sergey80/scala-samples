package object webapp {

  import _root_.rx._
  import org.scalajs.{dom => sdom}

  object all {

    import scalatags.JsDom.all._

    implicit class ElementOps[E <: sdom.Element](element: E)(implicit ctx: Ctx.Owner) {
      def bindOption[T](attr: Attr, item: Rx[Option[T]]): E = {
        item.foreach {
          case None => remove(attr)
          case Some(t) => set(attr, t)
        }
        element
      }

      def bind[T](attr: Attr, item: Rx[T])(implicit ctx: Ctx.Owner): E = {
        item.foreach{x =>
          set(attr, x)
        }
        element
      }

      private def set[T](attr: Attr, value: T): Unit = element.setAttribute(attr.name, value.toString)
      private def remove[T](attr: Attr): Unit = element.removeAttribute(attr.name)
    }
  }



}
