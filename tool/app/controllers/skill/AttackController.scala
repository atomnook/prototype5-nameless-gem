package controllers.skill

import javax.inject.Inject

import controllers.{FixedOps, OpsController}
import domain.ops.Ops
import domain.service.ServiceContext
import models.setter.skill.AttackSetter
import play.api.mvc.Call
import protobuf.skill.Attack
import views.html

class AttackController @Inject() (context: ServiceContext)
  extends OpsController[Attack, AttackSetter](context) with FixedOps[Attack, AttackSetter] {

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
}
