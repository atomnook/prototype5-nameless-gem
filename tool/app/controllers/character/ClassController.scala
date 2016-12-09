package controllers.character

import javax.inject.Inject

import controllers.AttributesController
import domain.ops.Ops
import domain.service.ServiceContext
import models.setter.character.ClassSetter
import play.api.mvc.Call
import protobuf.character.Class
import protobuf.core.{Attributes, Name}

class ClassController @Inject() (context: ServiceContext) extends AttributesController[Class, ClassSetter](context) {
  override protected[this] def tupled(a: Class): (Name, Attributes) = (a.name, a.getAttributes)

  override protected[this] val setCall: Call = routes.ClassController.set()

  override protected[this] val getCall: (String) => Call = routes.ClassController.get

  override protected[this] val deleteCall: (String) => Call = routes.ClassController.delete

  override protected[this] val ops: Ops[Class] = service.classes
}
