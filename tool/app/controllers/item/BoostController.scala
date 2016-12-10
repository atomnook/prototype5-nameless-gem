package controllers.item

import javax.inject.Inject

import controllers.AttributesController
import domain.ops.Ops
import domain.service.ServiceContext
import play.api.mvc.Call
import protobuf.core.NamedAttributes

class BoostController @Inject() (context: ServiceContext) extends AttributesController(context) {
  override protected[this] val setCall: Call = routes.BoostController.set()

  override protected[this] val getCall: (String) => Call = routes.BoostController.get

  override protected[this] val deleteCall: (String) => Call = routes.BoostController.delete

  override protected[this] val ops: Ops[NamedAttributes] = service.boosts
}
