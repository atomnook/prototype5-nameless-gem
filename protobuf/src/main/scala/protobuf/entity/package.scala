package protobuf

import protobuf.item.Equipment
import protobuf.skill.Attack

package object entity {
  private[this] def name(id: String): Name = Name.fromName(id).getOrElse(Name.Unrecognized(-1))

  implicit val character: Entity[Character] = Entity(_.getId == _.getId, id => Character().update(_.id.id := id))

  implicit val equipment: Entity[Equipment] = Entity(_.name == _.name, id => Equipment().update(_.name := name(id)))

  implicit val attack: Entity[Attack] = Entity(_.name == _.name, id => Attack().update(_.name := name(id)))
}
