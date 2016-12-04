package controllers

import domain.ops.Ops
import helpers.OpsControllerSpec
import play.api.mvc.Call
import protobuf.Character
import protobuf.arbitrary._

class CharacterControllerSpec extends OpsControllerSpec[Character] {
  override protected[this] def list: Call = routes.CharacterController.list()

  override protected[this] def get(a: Character): Call = routes.CharacterController.get(a.getId.id)

  override protected[this] def ops: Ops[Character] = service.characters

  override protected[this] def update(id: Character, data: Character): Character = data.update(_.id := id.getId)

  override protected[this] def fill(a: Character): Unit = {
    textField("id").value = a.getId.id
    textField("name").value = a.name
  }
}
