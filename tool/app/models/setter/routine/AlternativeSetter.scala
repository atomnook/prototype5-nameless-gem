package models.setter.routine

import models.ProtobufMutation
import models.value.{ConditionValue, Identifier}
import play.api.libs.json.{Json, OFormat}
import protobuf.routine.Alternative

case class AlternativeSetter(id: Identifier,
                             condition: ConditionValue,
                             satisfied: Option[Identifier],
                             otherwise: Option[Identifier])
  extends ProtobufMutation[Alternative] {

  override def toProtobuf: Alternative = {
    Alternative().update(
      _.id.id := id.id,
      _.condition := condition.value,
      _.optionalSatisfied := satisfied.map(_.expression),
      _.optionalOtherwise := otherwise.map(_.expression))
  }
}

object AlternativeSetter {
  implicit val format: OFormat[AlternativeSetter] = Json.format[AlternativeSetter]
}


