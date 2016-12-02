package domain.service

import java.util.Base64
import java.util.concurrent.atomic.AtomicReference

import protobuf.Database

case class ServiceContext(database: AtomicReference[Database]) {
  def write: String = Base64.getEncoder.encodeToString(database.get().toByteArray)
}

object ServiceContext {
  def apply(): ServiceContext = ServiceContext(new AtomicReference[Database](Database()))

  def apply(s: String): ServiceContext = {
    ServiceContext(new AtomicReference[Database](Database.parseFrom(Base64.getDecoder.decode(s))))
  }
}
