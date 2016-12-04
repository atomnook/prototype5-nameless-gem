package models.value

import play.api.libs.json.Format
import protobuf.routine.ActionSet

case class ActionSetValue(value: ActionSet) extends EnumValue[ActionSet]

object ActionSetValue {
  implicit val format: Format[ActionSetValue] = EnumValue.format(ActionSet, ActionSetValue.apply)
}
