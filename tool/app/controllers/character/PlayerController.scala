package controllers.character

import javax.inject.Inject

import domain.service.{DatabaseService, ServiceContext}
import play.api.mvc.{Action, Controller}
import views.html

class PlayerController @Inject() (context: ServiceContext) extends Controller {
  private[this] val service = DatabaseService(context)

  val list = Action(_ => Ok(html.character.PlayerController.list(service.players.get)))

  val newPlayer = Action(_ => Ok(html.character.PlayerController.newPlayer()))

  val create = TODO
}
