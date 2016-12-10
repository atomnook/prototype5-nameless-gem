package models.setter.core

import models.ProtobufMutation
import play.api.libs.json.{Json, OFormat}
import protobuf.core.Elements

case class ElementsSetter(unaspected: Long,
                          fire: Long,
                          ice: Long,
                          volt: Long,
                          water: Long,
                          earth: Long,
                          wind: Long,
                          light: Long,
                          dark: Long)
  extends ProtobufMutation[Elements] {

  override def toProtobuf: Elements = {
    val apply = Elements.apply _
    apply.tupled(Function.unlift(ElementsSetter.unapply)(this))
  }
}

object ElementsSetter {
  implicit val format: OFormat[ElementsSetter] = Json.format[ElementsSetter]
}
