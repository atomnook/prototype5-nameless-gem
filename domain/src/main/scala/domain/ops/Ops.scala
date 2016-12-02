package domain.ops

trait Ops[A] {
  def set(a: A): Unit

  def get: List[A]

  def delete(a: A): Unit
}
