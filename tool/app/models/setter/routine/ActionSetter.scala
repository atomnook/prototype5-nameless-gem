package models.setter.routine

import models.ProtobufMutation
import models.value.{ActionSetValue, ActionTypeValue, Identifier, NameValue}
import play.api.libs.json.{Json, OFormat}
import protobuf.routine.Action

case class ActionSetter(id: Identifier,
                        name: NameValue,
                        `type`: ActionTypeValue,
                        intersection: List[ActionSetValue],
                        otherwise: Option[Identifier])
  extends ProtobufMutation[Action] {

  override def toProtobuf: Action = {
    Action().update(
      _.id.id := id.id,
      _.name := name.value,
      _.`type` := `type`.value,
      _.intersection := intersection.map(_.value),
      _.optionalOtherwise := otherwise.map(_.expression))
  }
}

object ActionSetter {
  implicit val format: OFormat[ActionSetter] = Json.format[ActionSetter]
}
