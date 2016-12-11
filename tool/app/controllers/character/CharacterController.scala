package controllers.character

import javax.inject.Inject

import controllers.{FixedOps, OpsController}
import domain.ops.Ops
import domain.service.ServiceContext
import models.setter.character.CharacterSetter
import play.api.mvc.Call
import protobuf.character.Character
import views.html

class CharacterController @Inject() (context: ServiceContext)
  extends OpsController[Character, CharacterSetter](context) with FixedOps[Character, CharacterSetter] {

  override protected[this] def table(a: List[Character]): (HtmlContent) => HtmlContent = {
    html.character.CharacterController.table(a)
  }

  override protected[this] def input(a: Option[Character]): HtmlContent = {
    html.character.CharacterController.input(a, service.routines.get.map(_.getId))
  }

  override protected[this] def json(id: Option[String]): HtmlContent = {
    html.character.CharacterController.json(id)
  }

  override protected[this] val setCall: Call = routes.CharacterController.set()

  override protected[this] def deleteCall(id: String): Call = routes.CharacterController.delete(id)

  override protected[this] val ops: Ops[Character] = service.characters
}
