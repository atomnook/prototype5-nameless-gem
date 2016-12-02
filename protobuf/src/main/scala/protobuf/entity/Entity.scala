package protobuf.entity

class Entity[A](val equal: (A, A) => Boolean)

object Entity {
  def apply[A](equal: (A, A) => Boolean): Entity[A] = new Entity(equal)
}
