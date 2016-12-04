package controllers.skill

import domain.ops.Ops
import helpers.OpsControllerSpec
import play.api.mvc.Call
import protobuf.arbitrary._
import protobuf.skill.Attack

class AttackControllerSpec extends OpsControllerSpec[Attack] {
  override protected[this] def list: Call = routes.AttackController.list()

  override protected[this] def get(a: Attack): Call = routes.AttackController.get(a.name.name)

  override protected[this] def ops: Ops[Attack] = service.attacks

  override protected[this] def update(id: Attack, data: Attack): Attack = data.update(_.name := id.name)

  override protected[this] def fill(a: Attack): Unit = {
    singleSel("name").value = a.name.name
    singleSel("range").value = a.range.name
    numberField("tp").value = a.tp.toString
    numberField("atk").value = a.atk.toString
    numberField("spd").value = a.spd.toString
    numberField("hit").value = a.hit.toString
  }
}
