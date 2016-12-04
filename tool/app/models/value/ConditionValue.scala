package models.value

import play.api.libs.json.Format
import protobuf.routine.Condition

case class ConditionValue(value: Condition) extends EnumValue[Condition]

object ConditionValue {
  implicit val format: Format[ConditionValue] = EnumValue.format(Condition, ConditionValue.apply)
}
