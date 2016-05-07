package webapp

import scala.scalajs.js.annotation.JSExport

@JSExport
class SamplePage[Builder, Output <: FragT, FragT](val bundle: scalatags.generic.Bundle[Builder, Output, FragT]) {

  val htmlFrag = {
    import bundle.all._

    val inputBox = input(
      id:="new-todo",
      placeholder:="What needs to be done?",
      autofocus:=true
    )

    div(
      h1("ScalaTag's h1!"),
      form(
        inputBox
      ),
      label("label"),input(readonly)
    )
  }
}

