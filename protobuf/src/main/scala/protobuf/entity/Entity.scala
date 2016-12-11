package protobuf.entity

class Entity[A](val id: A => String, val identity: String => A) {
  def equal(a: A, b: A): Boolean = id(a) == id(b)
}

object Entity {
  def apply[A](id: A => String, identity: String => A): Entity[A] = new Entity(id, identity)
}
