package controllers.item

import javax.inject.Inject

import controllers.{FixedOps, OpsController}
import domain.ops.Ops
import domain.service.ServiceContext
import models.setter.item.EquipmentSetter
import play.api.mvc.Call
import protobuf.item.Equipment
import views.html

class EquipmentController @Inject() (context: ServiceContext)
  extends OpsController[Equipment, EquipmentSetter](context) with FixedOps[Equipment, EquipmentSetter] {

  override protected[this] def table(a: List[Equipment]): (HtmlContent) => HtmlContent = {
    html.item.EquipmentController.table(a)
  }

  override protected[this] def input(a: Option[Equipment]): HtmlContent = {
    html.item.EquipmentController.input(a)
  }

  override protected[this] def json(id: Option[String]): HtmlContent = {
    html.item.EquipmentController.json(id)
  }

  override protected[this] val setCall: Call = routes.EquipmentController.set()

  override protected[this] def deleteCall(id: String): Call = routes.EquipmentController.delete(id)

  override protected[this] val ops: Ops[Equipment] = service.equipments
}
