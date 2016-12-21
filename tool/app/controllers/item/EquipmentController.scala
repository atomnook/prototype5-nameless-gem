package controllers.item

import java.io.Reader
import javax.inject.Inject

import controllers.{FixedOps, OpsController}
import domain.ops.Ops
import domain.service.ServiceContext
import play.api.mvc.Call
import protobuf.item.{Equipment, EquipmentOuterClass}
import views.html

class EquipmentController @Inject() (context: ServiceContext)
  extends OpsController[Equipment](context) with FixedOps[Equipment] {

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

  override protected[this] def builder(json: Reader): Equipment = {
    EquipmentOuterClass.Equipment.newBuilder().parse(json, b => Equipment.fromJavaProto(b.build()))
  }
}
