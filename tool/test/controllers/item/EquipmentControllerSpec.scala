package controllers.item

import domain.ops.Ops
import helpers.OpsControllerSpec
import play.api.mvc.Call
import protobuf.arbitrary._
import protobuf.item.Equipment

class EquipmentControllerSpec extends OpsControllerSpec[Equipment] {
  override protected[this] def list: Call = routes.EquipmentController.list()

  override protected[this] def get(a: Equipment): Call = routes.EquipmentController.get(a.name.name)

  override protected[this] def ops: Ops[Equipment] = service.equipments

  override protected[this] def update(id: Equipment, data: Equipment): Equipment = data.update(_.name := id.name)

  override protected[this] def fill(a: Equipment): Unit = {
    singleSel("name").value = a.name.name
    numberField("price").value = a.price.toString
    numberField("atk").value = a.atk.toString
    numberField("def").value = a.`def`.toString
    numberField("mat").value = a.mat.toString
    numberField("mdf").value = a.mdf.toString
  }
}
