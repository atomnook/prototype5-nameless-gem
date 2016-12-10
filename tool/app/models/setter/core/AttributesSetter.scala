package models.setter.core

import models.ProtobufMutation
import play.api.libs.json.{Json, OFormat}
import protobuf.core.Attributes

case class AttributesSetter(hp: Long, tp: Long, str: Long, vit: Long, int: Long, wis: Long, agi: Long, luc: Long)
  extends ProtobufMutation[Attributes] {
  override def toProtobuf: Attributes = {
    val apply = Attributes.apply _
    apply.tupled(Function.unlift(AttributesSetter.unapply)(this))
  }
}

object AttributesSetter {
  implicit val format: OFormat[AttributesSetter] = Json.format[AttributesSetter]
}
