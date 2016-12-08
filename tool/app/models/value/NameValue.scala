package models.value

import play.api.libs.json.Format
import protobuf.core.Name

case class NameValue(value: Name) extends EnumValue[Name]

object NameValue {
  implicit val format: Format[NameValue] = EnumValue.format(Name, NameValue.apply)
}
