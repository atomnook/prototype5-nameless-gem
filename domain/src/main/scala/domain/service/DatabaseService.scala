package domain.service

import domain.ops.{DatabaseOps, Ops}
import protobuf.Character

class DatabaseService(context: ServiceContext) {
  def characters: Ops[Character] = DatabaseOps.apply(context.database, _.characters, _.characters)
}

object DatabaseService {
  def apply(context: ServiceContext): DatabaseService = new DatabaseService(context)
}
