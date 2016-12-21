package controllers.skill

import java.io.Reader
import javax.inject.Inject

import controllers.{FixedOps, OpsController}
import domain.ops.Ops
import domain.service.ServiceContext
import play.api.mvc.Call
import protobuf.skill.{Attack, AttackOuterClass}
import views.html

class AttackController @Inject() (context: ServiceContext)
  extends OpsController[Attack](context) with FixedOps[Attack] {

  override protected[this] def table(a: List[Attack]): (HtmlContent) => HtmlContent = {
    html.skill.AttackController.table(a)
  }

  override protected[this] def input(a: Option[Attack]): HtmlContent = {
    html.skill.AttackController.input(a)
  }

  override protected[this] def json(id: Option[String]): HtmlContent = {
    html.skill.AttackController.json(id)
  }

  override protected[this] val setCall: Call = routes.AttackController.set()

  override protected[this] def deleteCall(id: String): Call = routes.AttackController.delete(id)

  override protected[this] val ops: Ops[Attack] = service.attacks

  override protected[this] def builder(json: Reader): Attack = {
    AttackOuterClass.Attack.newBuilder().parse(json, b => Attack.fromJavaProto(b.build()))
  }
}
