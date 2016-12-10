package controllers.character

import domain.ops.Ops
import helpers.OpsControllerSpec
import play.api.mvc.Call
import protobuf.arbitrary._
import protobuf.character.Character
import protobuf.routine.Routine

class CharacterControllerSpec extends OpsControllerSpec[Character] {
  override protected[this] def list: Call = routes.CharacterController.list()

  override protected[this] def get(a: Character): Call = routes.CharacterController.get(a.getId.id)

  override protected[this] def ops: Ops[Character] = service.characters

  override protected[this] def update(id: Character, data: Character): Character = data.update(_.id := id.getId)

  override protected[this] def arbitrary2: (Character, Character) = {
    val (a, b) = super.arbitrary2

    Seq(a, b).foreach(c => service.routines.set(arbitrary1[Routine].update(_.id := c.getRoutine)))

    (a, b)
  }

  override protected[this] def fill(a: Character): Unit = {
    text("id") := a.getId.id
    text("name") := a.name
    select("routine") := a.getRoutine.id
  }
}
