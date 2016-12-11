package protobuf

import com.trueaccord.scalapb.{GeneratedEnum, GeneratedEnumCompanion}
import lib.implicits.Stream._
import org.scalacheck.{Arbitrary, Gen}
import protobuf.character.Character
import protobuf.core._
import protobuf.entity.Entity
import protobuf.item.Equipment
import protobuf.routine._
import protobuf.skill.{Attack, Range}

package object arbitrary {
  private[this] val positive = Gen.chooseNum(0, Int.MaxValue).map(_.toLong)

  private[this] def enum[A <: GeneratedEnum](c: GeneratedEnumCompanion[A]): Arbitrary[A] = Arbitrary(Gen.oneOf(c.values))

  private[this] def enums[A <: GeneratedEnum](c: GeneratedEnumCompanion[A]): Arbitrary[Seq[A]] = {
    Arbitrary(Gen.listOf(enum(c).arbitrary).map(_.toSet.toSeq.sortWith(_.value < _.value)))
  }

  implicit val attributesArbitrary: Arbitrary[Attributes] = {
    Arbitrary {
      Gen.infiniteStream(positive).map(positives => (Attributes.apply _).tupled(positives.tupled8))
    }
  }

  implicit val elementsArbitrary: Arbitrary[Elements] = {
    Arbitrary {
      Gen.infiniteStream(positive).map(positives => (Elements.apply _).tupled(positives.tupled9))
    }
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

  implicit val namedAttributesArbitrary: Arbitrary[NamedAttributes] = {
    Arbitrary {
      for {
        name <- enum(Name).arbitrary
        attributes <- attributesArbitrary.arbitrary
      } yield NamedAttributes().update(_.name := name, _.attributes := attributes)
    }
  }

  implicit val namedElementsArbitrary: Arbitrary[NamedElements] = {
    Arbitrary {
      for {
        name <- enum(Name).arbitrary
        elements <- elementsArbitrary.arbitrary
      } yield NamedElements().update(_.name := name, _.elements := elements)
    }
  }

  implicit val equipmentArbitrary: Arbitrary[Equipment] = {
    Arbitrary {
      for {
        name <- enum(Name).arbitrary
        positives <- Gen.infiniteStream(positive)
      } yield {
        Function.uncurried((Equipment.apply _).curried(name)(_)).tupled(positives.tupled5)
      }
    }
  }

  implicit val attackArbitrary: Arbitrary[Attack] = {
    Arbitrary {
      for {
        name <- enum(Name).arbitrary
        range <- enum(Range).arbitrary
        positives <- Gen.infiniteStream(positive)
      } yield {
        Function.uncurried((Attack.apply _).curried(name)(range)(_)).tupled(positives.tupled4)
      }
    }
  }

  implicit val alternativeArbitrary: Arbitrary[Alternative] = {
    Arbitrary {
      for {
        id <- Gen.identifier
        condition <- enum(Condition).arbitrary
        satisfied <- Gen.identifier
        otherwise <- Gen.identifier
      } yield {
        Alternative().update(
          _.id.id := id, _.condition := condition, _.satisfied.id := satisfied, _.otherwise.id := otherwise)
      }
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
        classes <- Gen.listOf(namedAttributesArbitrary.arbitrary)
        equipments <- Gen.listOf(equipmentArbitrary.arbitrary)
        boosts <- Gen.listOf(namedAttributesArbitrary.arbitrary)
        attacks <- Gen.listOf(attackArbitrary.arbitrary)
        routines <- Gen.listOf(routineArbitrary.arbitrary)
        elemental <- Gen.listOf(namedElementsArbitrary.arbitrary)
      } yield {
        Database().update(
          _.characters := characters,
          _.classes := classes,
          _.equipments := equipments,
          _.boosts := boosts,
          _.attacks := attacks,
          _.routines := routines,
          _.elemental := elemental)
      }
    }
  }

  def arbitrary1[A](implicit a: Arbitrary[A]): A = {
    Stream.continually(a.arbitrary.sample).flatten.head
  }

  def unique[A](implicit a: Arbitrary[A], e: Entity[A]): Stream[A] = {
    def rec(last: List[A], head: A): Stream[A] = {
      val current = head :: last
      val next = Stream.continually(a.arbitrary.sample).flatten.filter(v => !current.exists(e.equal(_, v))).head
      Stream.cons(head, rec(current, next))
    }
    rec(Nil, arbitrary1[A])
  }
}
