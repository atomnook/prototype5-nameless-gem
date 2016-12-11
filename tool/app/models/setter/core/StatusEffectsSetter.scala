package models.setter.core

import models.ProtobufMutation
import play.api.libs.json.{Json, OFormat}
import protobuf.core.StatusEffects

case class StatusEffectsSetter(poison: Long,
                               blind: Long,
                               silence: Long,
                               confusion: Long,
                               charm: Long,
                               sleep: Long) extends ProtobufMutation[StatusEffects] {

  override def toProtobuf: StatusEffects = {
    val apply = StatusEffects.apply _
    apply.tupled(Function.unlift(StatusEffectsSetter.unapply)(this))
  }
}

object StatusEffectsSetter {
  implicit val format: OFormat[StatusEffectsSetter] = Json.format[StatusEffectsSetter]
}
