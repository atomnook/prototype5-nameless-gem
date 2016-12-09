package controllers.character

import domain.ops.Ops
import helpers.AttributesControllerSpec
import play.api.mvc.Call
import protobuf.arbitrary._
import protobuf.character.Class
import protobuf.core.{Attributes, Name}

class ClassControllerSpec extends AttributesControllerSpec[Class] {
  override protected[this] def tupled(a: Class): (Name, Attributes) = (a.name, a.getAttributes)

  override protected[this] def list: Call = routes.ClassController.list()

  override protected[this] def get(a: Class): Call = routes.ClassController.get(a.name.name)

  override protected[this] def ops: Ops[Class] = service.classes

  override protected[this] def update(id: Class, data: Class): Class = data.update(_.name := id.name)
}
