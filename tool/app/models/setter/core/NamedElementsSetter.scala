package models.setter.core

import models.ProtobufMutation
import models.value.NameValue
import play.api.libs.json.{Json, OFormat}
import protobuf.core.NamedElements

case class NamedElementsSetter(name: NameValue, elements: ElementsSetter) extends ProtobufMutation[NamedElements] {
  override def toProtobuf: NamedElements = {
    NamedElements().update(_.name := name.value, _.elements := elements.toProtobuf)
  }
}

object NamedElementsSetter {
  implicit val format: OFormat[NamedElementsSetter] = Json.format[NamedElementsSetter]
}
