package protobuf

import protobuf.item.Equipment
import protobuf.routine.{Alternative, Routine}
import protobuf.skill.Attack

package object entity {
  private[this] def name(id: String): Name = Name.fromName(id).getOrElse(Name.Unrecognized(-1))

  implicit val characterEntity: Entity[Character] = Entity(_.getId == _.getId, id => Character().update(_.id.id := id))

  implicit val equipmentEntity: Entity[Equipment] = Entity(_.name == _.name, id => Equipment().update(_.name := name(id)))

  implicit val attackEntity: Entity[Attack] = Entity(_.name == _.name, id => Attack().update(_.name := name(id)))

  implicit val alternativeEntity: Entity[Alternative] = Entity(_.getId == _.getId, id => Alternative().update(_.id.id := id))

  implicit val routineEntity: Entity[Routine] = Entity(_.getId == _.getId, id => Routine().update(_.id.id := id))
}
