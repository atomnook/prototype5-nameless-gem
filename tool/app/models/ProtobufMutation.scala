package models

trait ProtobufMutation[A] {
  def toProtobuf: A
}
