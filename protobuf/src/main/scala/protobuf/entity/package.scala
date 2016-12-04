package protobuf

import protobuf.item.Equipment

package object entity {
  implicit val character: Entity[Character] = Entity(_.getId == _.getId, id => Character().update(_.id.id := id))

  implicit val equipment: Entity[Equipment] = Entity(
    _.name == _.name,
    id => Equipment().update(_.name := Name.fromName(id).getOrElse(Name.Unrecognized(-1))))
}
