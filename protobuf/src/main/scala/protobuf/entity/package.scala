package protobuf

import protobuf.character.Character
import protobuf.core.{Name, NamedAttributes, NamedElements}
import protobuf.item.Equipment
import protobuf.routine.{Alternative, Routine}
import protobuf.skill.Attack

package object entity {
  private[this] def name(id: String): Name = Name.fromName(id).getOrElse(Name.Unrecognized(-1))

  implicit val characterEntity: Entity[Character] = Entity(_.getId.id, id => Character().update(_.id.id := id))

  implicit val namedAttributesEntity: Entity[NamedAttributes] = Entity(_.name.name, id => NamedAttributes().update(_.name := name(id)))

  implicit val namedElementsEntity: Entity[NamedElements] = Entity(_.name.name, id => NamedElements().update(_.name := name(id)))

  implicit val equipmentEntity: Entity[Equipment] = Entity(_.name.name, id => Equipment().update(_.name := name(id)))

  implicit val attackEntity: Entity[Attack] = Entity(_.name.name, id => Attack().update(_.name := name(id)))

  implicit val alternativeEntity: Entity[Alternative] = Entity(_.getId.id, id => Alternative().update(_.id.id := id))

  implicit val routineEntity: Entity[Routine] = Entity(_.getId.id, id => Routine().update(_.id.id := id))
}
