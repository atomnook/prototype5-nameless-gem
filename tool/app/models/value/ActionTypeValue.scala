package models.value

import play.api.libs.json.Format
import protobuf.routine.ActionType

case class ActionTypeValue(value: ActionType) extends EnumValue[ActionType]

object ActionTypeValue {
  implicit val format: Format[ActionTypeValue] = EnumValue.format(ActionType, ActionTypeValue.apply)
}
