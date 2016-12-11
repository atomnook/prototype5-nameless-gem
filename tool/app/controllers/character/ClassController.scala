package controllers.character

import javax.inject.Inject

import controllers.AttributesController
import domain.ops.Ops
import domain.service.ServiceContext
import play.api.mvc.Call
import protobuf.core.NamedAttributes

class ClassController @Inject() (context: ServiceContext) extends AttributesController(context) {
  override protected[this] val setCall: Call = routes.ClassController.set()

  override protected[this] def getCall(id: String): Call = routes.ClassController.get(id)

  override protected[this] def deleteCall(id: String): Call = routes.ClassController.delete(id)

  override protected[this] val ops: Ops[NamedAttributes] = service.classes
}
