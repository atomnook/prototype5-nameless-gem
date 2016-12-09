package protobuf

import protobuf.character.Character
import protobuf.character.Class
import protobuf.core.Name
import protobuf.item.{Boost, Equipment}
import protobuf.routine.{Alternative, Routine}
import protobuf.skill.Attack

package object entity {
  private[this] def name(id: String): Name = Name.fromName(id).getOrElse(Name.Unrecognized(-1))

  implicit val characterEntity: Entity[Character] = Entity(_.getId == _.getId, id => Character().update(_.id.id := id))

  implicit val classEntity: Entity[Class] = Entity(_.name == _.name, id => Class().update(_.name := name(id)))

  implicit val equipmentEntity: Entity[Equipment] = Entity(_.name == _.name, id => Equipment().update(_.name := name(id)))

  implicit val boostEntity: Entity[Boost] = Entity(_.name == _.name, id => Boost().update(_.name := name(id)))

  implicit val attackEntity: Entity[Attack] = Entity(_.name == _.name, id => Attack().update(_.name := name(id)))

  implicit val alternativeEntity: Entity[Alternative] = Entity(_.getId == _.getId, id => Alternative().update(_.id.id := id))

  implicit val routineEntity: Entity[Routine] = Entity(_.getId == _.getId, id => Routine().update(_.id.id := id))
}
