package controllers.character

import javax.inject.Inject

import controllers.OpsController
import domain.ops.Ops
import domain.service.ServiceContext
import models.setter.character.CharacterSetter
import protobuf.character.Character
import views.html

class CharacterController @Inject() (context: ServiceContext)
  extends OpsController[Character, CharacterSetter](context) {

  override protected[this] val ops: Ops[Character] = service.characters

  override protected[this] def list(a: List[Character]): HtmlContent = {
    html.character.CharacterController.list(a, service.routines.get.map(_.getId))
  }

  override protected[this] def get(a: Character): HtmlContent = {
    html.character.CharacterController.get(a, service.routines.get.map(_.getId))
  }
}
