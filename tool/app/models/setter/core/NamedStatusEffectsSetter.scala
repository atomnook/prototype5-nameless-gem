package models.setter.core

import models.ProtobufMutation
import models.value.NameValue
import play.api.libs.json.{Json, OFormat}
import protobuf.core.NamedStatusEffects

case class NamedStatusEffectsSetter(name: NameValue, effects: StatusEffectsSetter)
  extends ProtobufMutation[NamedStatusEffects] {

  override def toProtobuf: NamedStatusEffects = {
    NamedStatusEffects().update(_.name := name.value, _.statusEffects := effects.toProtobuf)
  }
}

object NamedStatusEffectsSetter {
  implicit val format: OFormat[NamedStatusEffectsSetter] = Json.format[NamedStatusEffectsSetter]
}
