package domain.service

import domain.ops.Ops
import lib.implicits.Stream._
import org.scalacheck.Arbitrary
import org.scalacheck.Prop._
import org.scalatest.FlatSpec
import org.scalatest.prop.Checkers
import protobuf.arbitrary._
import protobuf.entity.Entity

class DatabaseServiceSpec extends FlatSpec with Checkers {
  private[this] def ops[A](name: String, f: DatabaseService => Ops[A])(implicit a: Arbitrary[A], e: Entity[A]) = {
    it should s"have set/get Ops[$name]" in {
      check { v: A =>
        val service = DatabaseService(ServiceContext())

        f(service).set(v)
        f(service).get == v :: Nil
      }
    }

    it should s"have delete Ops[$name]" in {
      val service = DatabaseService(ServiceContext())
      val (v1, v2) = unique[A].tupled2

      f(service).set(v1)
      f(service).set(v2)
      f(service).get == v1 :: v2 :: Nil

      f(service).delete(v1)
      f(service).get == v2 :: Nil

      f(service).delete(v2)
      f(service).get == Nil
    }
  }

  ops("character.Character", _.characters)

  ops("core.NamedAttributes (classes)", _.classes)

  ops("item.Equipment", _.equipments)

  ops("core.NamedAttributes (boosts)", _.boosts)

  ops("skill.Attack", _.attacks)

  ops("routine.Routine", _.routines)

  ops("core.NamedElement", _.elemental)

  ops("core.NamedStatusEffect", _.statusEffects)
}
