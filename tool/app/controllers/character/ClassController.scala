package controllers.character

import javax.inject.Inject

import controllers.AttributesController
import domain.ops.Ops
import domain.service.ServiceContext
import play.api.mvc.Call
import protobuf.core.NamedAttributes

class ClassController @Inject() (context: ServiceContext) extends AttributesController(context) {
  override protected[this] val setCall: Call = routes.ClassController.set()

  override protected[this] val getCall: (String) => Call = routes.ClassController.get

  override protected[this] val deleteCall: (String) => Call = routes.ClassController.delete

  override protected[this] val ops: Ops[NamedAttributes] = service.classes
}
