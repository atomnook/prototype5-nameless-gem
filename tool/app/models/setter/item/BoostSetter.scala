package models.setter.item

import models.ProtobufMutation
import models.setter.common.AttributesSetter
import models.value.NameValue
import play.api.libs.json.{Json, OFormat}
import protobuf.item.Boost

case class BoostSetter(name: NameValue, attributes: AttributesSetter) extends ProtobufMutation[Boost] {
  override def toProtobuf: Boost = Boost().update(_.name := name.value, _.attributes := attributes.toProtobuf)
}

object BoostSetter {
  implicit val format: OFormat[BoostSetter] = Json.format[BoostSetter]
}
