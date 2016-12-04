package models.setter.item

import models.ProtobufMutation
import models.value.NameValue
import play.api.libs.json.{Json, OFormat}
import protobuf.item.Equipment

case class EquipmentSetter(name: NameValue, price: Long, atk: Long, `def`: Long, mat: Long, mdf: Long)
  extends ProtobufMutation[Equipment] {
  override def toProtobuf: Equipment = {
    Equipment().update(
      _.name := name.value, _.price := price, _.atk := atk, _.`def` := `def`, _.mat := mat, _.mdf := mdf)
  }
}

object EquipmentSetter {
  implicit val format: OFormat[EquipmentSetter] = Json.format[EquipmentSetter]
}
