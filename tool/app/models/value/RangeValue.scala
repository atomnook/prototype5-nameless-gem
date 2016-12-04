package models.value

import play.api.libs.json.Format
import protobuf.skill.Range

case class RangeValue(value: Range) extends EnumValue[Range]

object RangeValue {
  implicit val format: Format[RangeValue] = EnumValue.format(Range, RangeValue.apply)
}
