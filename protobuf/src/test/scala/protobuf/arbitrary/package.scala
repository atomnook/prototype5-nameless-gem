package protobuf

import com.trueaccord.scalapb.{GeneratedEnum, GeneratedEnumCompanion}
import org.scalacheck.{Arbitrary, Gen}
import protobuf.character.Character
import protobuf.core.Name
import protobuf.entity.Entity
import protobuf.item.Equipment
import protobuf.routine._
import protobuf.skill.{Attack, Range}

package object arbitrary {
  private[this] val positive = Gen.chooseNum(0, Int.MaxValue)

  private[this] def enum[A <: GeneratedEnum](c: GeneratedEnumCompanion[A]): Arbitrary[A] = Arbitrary(Gen.oneOf(c.values))

  private[this] def enums[A <: GeneratedEnum](c: GeneratedEnumCompanion[A]): Arbitrary[Seq[A]] = {
    Arbitrary(Gen.listOf(enum(c).arbitrary).map(_.toSet.toSeq.sortWith(_.value < _.value)))
  }

  implicit val conditionArbitrary: Arbitrary[Condition] = enum(Condition)

  implicit val characterArbitrary: Arbitrary[Character] = {
    Arbitrary {
      for {
        id <- Gen.identifier
        name <- Gen.identifier
        routine <- Gen.identifier
      } yield Character().update(_.id.id := id, _.name := name, _.routine.id := routine)
    }
  }

  implicit val equipmentArbitrary: Arbitrary[Equipment] = {
    Arbitrary {
      for {
        name <- enum(Name).arbitrary
        price <- positive
        atk <- positive
        def_ <- positive
        mat <- positive
        mdf <- positive
      } yield Equipment().update(
        _.name := name, _.price := price, _.atk := atk, _.`def` := def_, _.mat := mat, _.mdf := mdf)
    }
  }

  implicit val attackArbitrary: Arbitrary[Attack] = {
    Arbitrary {
      for {
        name <- enum(Name).arbitrary
        range <- enum(Range).arbitrary
        tp <- positive
        atk <- positive
        spd <- positive
        hit <- positive
      } yield Attack().update(_.name := name, _.range := range, _.tp := tp, _.atk := atk, _.spd := spd, _.hit := hit)
    }
  }

  implicit val alternativeArbitrary: Arbitrary[Alternative] = {
    Arbitrary {
      for {
        id <- Gen.identifier
        condition <- enum(Condition).arbitrary
        then_ <- Gen.identifier
        else_ <- Gen.identifier
      } yield Alternative().update(_.id.id := id, _.condition := condition, _.`then`.id := then_, _.`else`.id := else_)
    }
  }

  implicit val actionArbitrary: Arbitrary[Action] = {
    Arbitrary {
      for {
        id <- Gen.identifier
        name <- enum(Name).arbitrary
        type_ <- enum(ActionType).arbitrary
        intersection <- enums(ActionSet).arbitrary
        otherwise <- Gen.identifier
      } yield {
        Action().update(
          _.id.id := id, _.name := name, _.`type` := type_, _.intersection := intersection, _.otherwise.id := otherwise)
      }
    }
  }

  implicit val routineArbitrary: Arbitrary[Routine] = {
    Arbitrary {
      for {
        id <- Gen.identifier
        alternatives <- Gen.listOf(alternativeArbitrary.arbitrary)
        actions <- Gen.listOf(actionArbitrary.arbitrary)
      } yield Routine().update(_.id.id := id, _.alternatives := alternatives, _.actions := actions)
    }
  }

  implicit val databaseArbitrary: Arbitrary[Database] = {
    Arbitrary {
      for {
        characters <- Gen.listOf(characterArbitrary.arbitrary)
        equipments <- Gen.listOf(equipmentArbitrary.arbitrary)
        attacks <- Gen.listOf(attackArbitrary.arbitrary)
        routines <- Gen.listOf(routineArbitrary.arbitrary)
      } yield {
        Database().update(
          _.characters := characters, _.equipments := equipments, _.attacks := attacks, _.routines := routines)
      }
    }
  }

  def unique[A](size: Int)(implicit a: Arbitrary[A], e: Entity[A]): List[A] = {
    (1 to size).foldLeft(List.empty[A]) { case (res, _) =>
      res :+ Stream.continually(a.arbitrary.sample).flatten.filter(value => !res.exists(e.equal(_, value))).head
    }
  }

  private[this] def arbitraryN[A, B](a: List[A])(f: PartialFunction[List[A], B]): B = f.apply(a)

  def arbitrary1[A](implicit a: Arbitrary[A]): A = {
    Stream.continually(a.arbitrary.sample).flatten.head
  }

  def arbitrary2[A](implicit a: Arbitrary[A], e: Entity[A]): (A, A) = {
    arbitraryN(unique(2)(a, e)) {
      case v1 :: v2 :: Nil => (v1, v2)
    }
  }
}
