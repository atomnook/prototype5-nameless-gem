package protobuf.entity

class Entity[A](val equal: (A, A) => Boolean, val identity: String => A)

object Entity {
  def apply[A](equal: (A, A) => Boolean, identity: String => A): Entity[A] = new Entity(equal, identity)
}
