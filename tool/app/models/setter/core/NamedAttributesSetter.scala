package models.setter.core

import models.ProtobufMutation
import models.value.NameValue
import play.api.libs.json.{Json, OFormat}
import protobuf.core.NamedAttributes

case class NamedAttributesSetter(name: NameValue, attributes: AttributesSetter) extends ProtobufMutation[NamedAttributes] {
  override def toProtobuf: NamedAttributes = {
    NamedAttributes().update(_.name := name.value, _.attributes := attributes.toProtobuf)
  }
}

object NamedAttributesSetter {
  implicit val format: OFormat[NamedAttributesSetter] = Json.format[NamedAttributesSetter]
}
