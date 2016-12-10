package controllers.character

import domain.ops.Ops
import helpers.AttributesControllerSpec
import play.api.mvc.Call
import protobuf.core.NamedAttributes

class ClassControllerSpec extends AttributesControllerSpec {
  override protected[this] def list: Call = routes.ClassController.list()

  override protected[this] def get(a: NamedAttributes): Call = routes.ClassController.get(a.name.name)

  override protected[this] def ops: Ops[NamedAttributes] = service.classes
}
