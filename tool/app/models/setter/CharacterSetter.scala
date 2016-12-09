package models.setter

import models.ProtobufMutation
import models.value.Identifier
import play.api.libs.json.{Json, OFormat}
import protobuf.character.Character

case class CharacterSetter(id: Identifier, name: String, routine: Identifier) extends ProtobufMutation[Character] {
  override def toProtobuf: Character = Character().update(_.id.id := id.id, _.name := name, _.routine.id := routine.id)
}

object CharacterSetter {
  implicit val format: OFormat[CharacterSetter] = Json.format[CharacterSetter]
}
