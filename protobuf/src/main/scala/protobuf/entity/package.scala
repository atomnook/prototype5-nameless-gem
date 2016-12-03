package protobuf

package object entity {
  implicit val character: Entity[Character] = Entity(_.getId == _.getId, id => Character().update(_.id.id := id))
}
