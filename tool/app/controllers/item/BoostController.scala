package controllers.item

import javax.inject.Inject

import controllers.AttributesController
import domain.ops.Ops
import domain.service.ServiceContext
import play.api.mvc.Call
import protobuf.core.NamedAttributes

class BoostController @Inject() (context: ServiceContext) extends AttributesController(context) {
  override protected[this] val setCall: Call = routes.BoostController.set()

  override protected[this] def getCall(id: String): Call = routes.BoostController.get(id)

  override protected[this] def deleteCall(id: String): Call = routes.BoostController.delete(id)

  override protected[this] val ops: Ops[NamedAttributes] = service.boosts
}
