package controllers.item

import javax.inject.Inject

import controllers.AttributesController
import domain.ops.Ops
import domain.service.ServiceContext
import models.setter.item.BoostSetter
import play.api.mvc.Call
import protobuf.core.{Attributes, Name}
import protobuf.item.Boost

class BoostController @Inject() (context: ServiceContext) extends AttributesController[Boost, BoostSetter](context) {
  override protected[this] def tupled(a: Boost): (Name, Attributes) = (a.name, a.getAttributes)

  override protected[this] val setCall: Call = routes.BoostController.set()

  override protected[this] val getCall: (String) => Call = routes.BoostController.get

  override protected[this] val deleteCall: (String) => Call = routes.BoostController.delete

  override protected[this] val ops: Ops[Boost] = service.boosts
}
