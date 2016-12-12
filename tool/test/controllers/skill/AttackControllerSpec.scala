package controllers.skill

import domain.ops.Ops
import helpers.{ChromeSpec, FirefoxSpec, OpsControllerSpec}
import play.api.mvc.Call
import protobuf.arbitrary._
import protobuf.skill.Attack

abstract class AttackControllerSpec extends OpsControllerSpec[Attack] {
  override protected[this] def list: Call = routes.AttackController.list()

  override protected[this] def get(a: Attack): Call = routes.AttackController.get(a.name.name)

  override protected[this] def ops: Ops[Attack] = service.attacks

  override protected[this] def update(id: Attack, data: Attack): Attack = data.update(_.name := id.name)

  override protected[this] def fill(a: Attack): Unit = {
    enumSelect("name") := a.name
    enumSelect("range") := a.range
    number("tp") := a.tp
    number("atk") := a.atk
    number("spd") := a.spd
    number("hit") := a.hit
  }
}

class FirefoxAttackControllerSpec extends AttackControllerSpec with FirefoxSpec

class ChromeAttackControllerSpec extends AttackControllerSpec with ChromeSpec
