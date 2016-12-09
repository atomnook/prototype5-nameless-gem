package models.setter.character

import models.ProtobufMutation
import models.setter.common.AttributesSetter
import models.value.NameValue
import play.api.libs.json.{Json, OFormat}
import protobuf.character.Class

case class ClassSetter(name: NameValue, attributes: AttributesSetter) extends ProtobufMutation[Class] {
  override def toProtobuf: Class = Class().update(_.name := name.value, _.attributes := attributes.toProtobuf)
}

object ClassSetter {
  implicit val format: OFormat[ClassSetter] = Json.format[ClassSetter]
}
