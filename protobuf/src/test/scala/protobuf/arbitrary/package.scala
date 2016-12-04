package protobuf

import com.trueaccord.scalapb.{GeneratedEnum, GeneratedEnumCompanion}
import org.scalacheck.{Arbitrary, Gen}
import protobuf.entity.Entity
import protobuf.item.Equipment
import protobuf.skill.{Attack, Range}

package object arbitrary {
  private[this] val positive = Gen.chooseNum(0, Int.MaxValue)

  private[this] def enum[A <: GeneratedEnum](c: GeneratedEnumCompanion[A]): Arbitrary[A] = Arbitrary(Gen.oneOf(c.values))

  implicit val character: Arbitrary[Character] = {
    Arbitrary {
      for {
        id <- Gen.identifier
        name <- Gen.identifier
      } yield Character().update(_.id.id := id, _.name := name)
    }
  }

  implicit val equipment: Arbitrary[Equipment] = {
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

  implicit val attack: Arbitrary[Attack] = {
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

  implicit val database: Arbitrary[Database] = {
    Arbitrary {
      for {
        characters <- Gen.listOf(character.arbitrary)
      } yield Database().update(_.characters := characters)
    }
  }

  def unique[A](size: Int)(implicit a: Arbitrary[A], e: Entity[A]): List[A] = {
    (1 to size).foldLeft(List.empty[A]) { case (res, _) =>
      res :+ Stream.continually(a.arbitrary.sample).flatten.filter(value => !res.exists(e.equal(_, value))).head
    }
  }

  private[this] def arbitraryN[A, B](a: List[A])(f: PartialFunction[List[A], B]): B = f.apply(a)

  def arbitrary1[A](implicit a: Arbitrary[A], e: Entity[A]): A = {
    arbitraryN(unique(1)(a, e)) {
      case v1 :: Nil => v1
    }
  }

  def arbitrary2[A](implicit a: Arbitrary[A], e: Entity[A]): (A, A) = {
    arbitraryN(unique(2)(a, e)) {
      case v1 :: v2 :: Nil => (v1, v2)
    }
  }
}
