package protobuf

import protobuf.core.{Name, NamedAttributes, NamedElements, NamedStatusEffects}
import protobuf.item.Equipment
import protobuf.skill.Attack

package object entity {
  private[this] def name(id: String): Name = Name.fromName(id).getOrElse(Name.Unrecognized(-1))

  implicit val namedAttributesEntity: Entity[NamedAttributes] = Entity(_.name.name, id => NamedAttributes().update(_.name := name(id)))

  implicit val namedElementsEntity: Entity[NamedElements] = Entity(_.name.name, id => NamedElements().update(_.name := name(id)))

  implicit val namedStatusEffectsEntity: Entity[NamedStatusEffects] = Entity(_.name.name, id => NamedStatusEffects().update(_.name := name(id)))

  implicit val equipmentEntity: Entity[Equipment] = Entity(_.name.name, id => Equipment().update(_.name := name(id)))

  implicit val attackEntity: Entity[Attack] = Entity(_.name.name, id => Attack().update(_.name := name(id)))
}
