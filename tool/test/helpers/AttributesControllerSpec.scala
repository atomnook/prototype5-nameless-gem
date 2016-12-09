package helpers

import org.scalacheck.Arbitrary
import protobuf.core.{Attributes, Name}
import protobuf.entity.Entity

abstract class AttributesControllerSpec[A](implicit arbitrary: Arbitrary[A], entity: Entity[A]) extends OpsControllerSpec[A] {
  protected[this] def tupled(a: A): (Name, Attributes)

  override protected[this] def fill(a: A): Unit = {
    val (n, at) = tupled(a)

    singleSel("name").value = n.name

    Seq(
      ("hp", at.hp),
      ("tp", at.tp),
      ("str", at.str),
      ("vit", at.vit),
      ("int", at.int),
      ("wis", at.wis),
      ("agi", at.agi),
      ("luc", at.luc)).foreach { case (id, v) =>
      numberField(id).value = v.toString
    }
  }
}
