package controllers

import domain.ops.Ops
import helpers.OpsControllerSpec
import play.api.mvc.Call
import protobuf.arbitrary._
import protobuf.core.NamedStatusEffects

class StatusEffectsControllerSpec extends OpsControllerSpec[NamedStatusEffects] {
  override protected[this] def list: Call = routes.StatusEffectsController.list()

  override protected[this] def get(a: NamedStatusEffects): Call = routes.StatusEffectsController.get(a.name.name)

  override protected[this] def ops: Ops[NamedStatusEffects] = service.statusEffects

  override protected[this] def update(id: NamedStatusEffects, data: NamedStatusEffects): NamedStatusEffects = {
    data.update(_.name := id.name)
  }

  override protected[this] def fill(a: NamedStatusEffects): Unit = {
    enumSelect("name") := a.name

    val s = a.getStatusEffects
    Seq(
      ("poison", s.poison),
      ("blind", s.blind),
      ("silence", s.silence),
      ("confusion", s.confusion),
      ("charm", s.charm),
      ("sleep", s.sleep)).foreach { case (k, v) =>
      number(k) := v
    }
  }
}
