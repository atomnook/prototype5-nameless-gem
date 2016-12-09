package domain.service

import com.trueaccord.lenses.Lens
import domain.ops.{DatabaseOps, Ops}
import protobuf.Database
import protobuf.Database.DatabaseLens
import protobuf.character.{Character, Class}
import protobuf.entity.Entity
import protobuf.item.{Boost, Equipment}
import protobuf.routine.Routine
import protobuf.skill.Attack

class DatabaseService(context: ServiceContext) {
  private[this] def ops[A : Entity](f: Database => Seq[A],
                                    g: DatabaseLens[Database] => Lens[Database, Seq[A]]): DatabaseOps[A] = {
    DatabaseOps.apply[A](context.database, f, g)
  }

  def characters: Ops[Character] = ops(_.characters, _.characters)

  def classes: Ops[Class] = ops(_.classes, _.classes)

  def equipments: Ops[Equipment] = ops(_.equipments, _.equipments)

  def boosts: Ops[Boost] = ops(_.boosts, _.boosts)

  def attacks: Ops[Attack] = ops(_.attacks, _.attacks)

  def routines: Ops[Routine] = ops(_.routines, _.routines)
}

object DatabaseService {
  def apply(context: ServiceContext): DatabaseService = new DatabaseService(context)
}
