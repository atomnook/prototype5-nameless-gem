package models.setter.skill

import models.ProtobufMutation
import models.value.{NameValue, RangeValue}
import play.api.libs.json.{Json, OFormat}
import protobuf.skill.Attack

case class AttackSetter(name: NameValue, range: RangeValue, tp: Long, atk: Long, spd: Long, hit: Long)
  extends ProtobufMutation[Attack] {

  override def toProtobuf: Attack = {
    Attack().update(_.name := name.value, _.range := range.value, _.tp := tp, _.atk := atk, _.spd := spd, _.hit := hit)
  }
}

object AttackSetter {
  implicit val format: OFormat[AttackSetter] = Json.format[AttackSetter]
}
