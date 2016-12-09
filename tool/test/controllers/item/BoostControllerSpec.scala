package controllers.item

import domain.ops.Ops
import helpers.AttributesControllerSpec
import play.api.mvc.Call
import protobuf.arbitrary._
import protobuf.core.{Attributes, Name}
import protobuf.item.Boost

class BoostControllerSpec extends AttributesControllerSpec[Boost] {
  override protected[this] def tupled(a: Boost): (Name, Attributes) = (a.name, a.getAttributes)

  override protected[this] def list: Call = routes.BoostController.list()

  override protected[this] def get(a: Boost): Call = routes.BoostController.get(a.name.name)

  override protected[this] def ops: Ops[Boost] = service.boosts

  override protected[this] def update(id: Boost, data: Boost): Boost = data.update(_.name := id.name)
}
