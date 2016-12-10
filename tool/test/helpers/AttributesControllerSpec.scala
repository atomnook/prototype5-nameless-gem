package helpers

import protobuf.arbitrary._
import protobuf.core.NamedAttributes

abstract class AttributesControllerSpec extends OpsControllerSpec[NamedAttributes] {
  override protected[this] def update(id: NamedAttributes, data: NamedAttributes): NamedAttributes = {
    data.update(_.name := id.name)
  }

  override protected[this] def fill(a: NamedAttributes): Unit = {
    val (n, at) = (a.name, a.getAttributes)

    singleSel("name").value = n.name

    Seq(
      ("hp", at.hp),
      ("tp", at.tp),
      ("str", at.str),
      ("vit", at.vit),
      ("int", at.int),
      ("wis", at.wis),
      ("agi", at.agi),
      ("luc", at.luc)).foreach { case (id, v) =>
      numberField(id).value = v.toString
    }
  }
}
