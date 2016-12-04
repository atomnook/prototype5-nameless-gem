package models.setter.item

import models.ProtobufMutation
import models.value.name
import play.api.libs.json.{Json, OFormat}
import protobuf.Name
import protobuf.item.Equipment

case class EquipmentSetter(name: Name, price: Long, atk: Long, `def`: Long, mat: Long, mdf: Long) extends ProtobufMutation[Equipment] {
  override def toProtobuf: Equipment = {
    Equipment().update(_.name := name, _.price := price, _.atk := atk, _.`def` := `def`, _.mat := mat, _.mdf := mdf)
  }
}

object EquipmentSetter {
  implicit val format: OFormat[EquipmentSetter] = Json.format[EquipmentSetter]
}
