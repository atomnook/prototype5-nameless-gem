package controllers.item

import javax.inject.Inject

import controllers.OpsController
import domain.ops.Ops
import domain.service.ServiceContext
import models.setter.item.EquipmentSetter
import protobuf.entity._
import protobuf.item.Equipment
import views.html

class EquipmentController @Inject() (context: ServiceContext)
  extends OpsController[Equipment, EquipmentSetter](context) {

  override protected[this] val ops: Ops[Equipment] = service.equipments

  override protected[this] def list(a: List[Equipment]): HtmlContent = html.item.EquipmentController.list(a)

  override protected[this] def get(a: Equipment): HtmlContent = html.item.EquipmentController.get(a)
}
