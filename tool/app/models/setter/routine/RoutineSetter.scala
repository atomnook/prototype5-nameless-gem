package models.setter.routine

import models.ProtobufMutation
import models.value.Identifier
import play.api.libs.json.{Json, OFormat}
import protobuf.routine.Routine

case class RoutineSetter(id: Identifier, alternatives: List[AlternativeSetter], actions: List[ActionSetter])
  extends ProtobufMutation[Routine] {

  override def toProtobuf: Routine = {
    Routine().update(
      _.id.id := id.id, _.alternatives := alternatives.map(_.toProtobuf), _.actions := actions.map(_.toProtobuf))
  }
}

object RoutineSetter {
  implicit val format: OFormat[RoutineSetter] = Json.format[RoutineSetter]
}
