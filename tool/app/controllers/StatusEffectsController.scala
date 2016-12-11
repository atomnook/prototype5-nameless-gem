package controllers

import javax.inject.Inject

import domain.ops.Ops
import domain.service.ServiceContext
import models.setter.core.NamedStatusEffectsSetter
import play.api.mvc.Call
import protobuf.core.NamedStatusEffects
import views.html

class StatusEffectsController @Inject() (context: ServiceContext)
  extends OpsController[NamedStatusEffects, NamedStatusEffectsSetter](context)
    with FixedOps[NamedStatusEffects, NamedStatusEffectsSetter] {

  override protected[this] def table(a: List[NamedStatusEffects]): (HtmlContent) => HtmlContent = {
    html.StatusEffectsController.table(a)
  }

  override protected[this] def input(a: Option[NamedStatusEffects]): HtmlContent = {
    html.StatusEffectsController.input(a)
  }

  override protected[this] def json(id: Option[String]): HtmlContent = {
    html.StatusEffectsController.json(id)
  }

  override protected[this] val setCall: Call = routes.StatusEffectsController.set()

  override protected[this] def deleteCall(id: String): Call = routes.StatusEffectsController.delete(id)

  override protected[this] val ops: Ops[NamedStatusEffects] = service.statusEffects
}
