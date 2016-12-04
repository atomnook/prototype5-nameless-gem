package domain.service

import domain.ops.{DatabaseOps, Ops}
import protobuf.Character
import protobuf.item.Equipment

class DatabaseService(context: ServiceContext) {
  def characters: Ops[Character] = DatabaseOps.apply(context.database, _.characters, _.characters)

  def equipments: Ops[Equipment] = DatabaseOps.apply(context.database, _.equipments, _.equipments)
}

object DatabaseService {
  def apply(context: ServiceContext): DatabaseService = new DatabaseService(context)
}
