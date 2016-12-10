package controllers

import domain.ops.Ops
import helpers.OpsControllerSpec
import play.api.mvc.Call
import protobuf.arbitrary._
import protobuf.core.NamedElements

class ElementsControllerSpec extends OpsControllerSpec[NamedElements] {
  override protected[this] def list: Call = routes.ElementsController.list()

  override protected[this] def get(a: NamedElements): Call = routes.ElementsController.get(a.name.name)

  override protected[this] def ops: Ops[NamedElements] = service.elemental

  override protected[this] def update(id: NamedElements, data: NamedElements): NamedElements = data.update(_.name := id.name)

  override protected[this] def fill(a: NamedElements): Unit = {
    singleSel("name").value = a.name.name

    val e = a.getElements
    Seq(
      ("unaspected", e.unaspected),
      ("fire", e.fire),
      ("ice", e.ice),
      ("volt", e.volt),
      ("water", e.water),
      ("earth", e.earth),
      ("wind", e.wind),
      ("light", e.light),
      ("dark", e.dark)).foreach { case (q, v) =>
      numberField(q).value = v.toString
    }
  }
}
