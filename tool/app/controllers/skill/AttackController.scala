package controllers.skill

import javax.inject.Inject

import controllers.OpsController
import domain.ops.Ops
import domain.service.ServiceContext
import models.setter.skill.AttackSetter
import protobuf.skill.Attack
import views.html

class AttackController @Inject() (context: ServiceContext) extends OpsController[Attack, AttackSetter](context) {
  override protected[this] val ops: Ops[Attack] = service.attacks

  override protected[this] def list(a: List[Attack]): HtmlContent = html.skill.AttackController.list(a)

  override protected[this] def get(a: Attack): HtmlContent = html.skill.AttackController.get(a)
}
