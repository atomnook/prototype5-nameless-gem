package controllers

import java.util.UUID
import javax.inject.Inject

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.stream.Materializer
import controllers.BoardController.PlayActor
import domain.service.{DatabaseService, ServiceContext}
import models.value.Identifier
import play.api.libs.json._
import play.api.libs.streams.ActorFlow
import play.api.mvc.{Action, AnyContent, Controller, WebSocket}
import views.html

class BoardController @Inject() (implicit context: ServiceContext, system: ActorSystem, materializer: Materializer)
  extends Controller {

  private[this] val service = DatabaseService(context)

  val board: Action[AnyContent] = Action(_ => Ok(html.BoardController.board(characters = service.characters.get)))

  val play: WebSocket = WebSocket.accept[JsValue, JsValue] { _ =>
    ActorFlow.actorRef(out => Props(new PlayActor(out)))
  }
}

object BoardController {
  private[this] case class Lines(front: List[Identifier], rear: List[Identifier])

  private[this] case class PlayRequest(friends: Lines, enemies: Lines)

  private[this] case class PlayResponse(accepted: Option[String], errors: Option[JsValue])

  private[this] case class PlayMessage(message: String)

  private[this] implicit val linesFormat: OFormat[Lines] = Json.format[Lines]

  private[this] implicit val requestFormat: OFormat[PlayRequest] = Json.format[PlayRequest]

  private[this] implicit val responseFormat: OFormat[PlayResponse] = Json.format[PlayResponse]

  private[this] implicit val messageFormat: OFormat[PlayMessage] = Json.format[PlayMessage]

  private class PlayActor(out: ActorRef) extends Actor {
    private[this] var playId: Option[UUID] = None

    private[this] def send(message: String): Unit = {
      out ! Json.toJson(PlayMessage(s"$message"))
    }

    private[this] def play(request: PlayRequest): PlayResponse = {
      playId match {
        case Some(id) =>
          send(s"$id already started.")
          PlayResponse(accepted = None, errors = None)

        case None =>
          val id = UUID.randomUUID()
          playId = Some(id)
          send(s"$id started.")
          PlayResponse(accepted = playId.map(_.toString), errors = None)
      }
    }

    override def receive: Receive = {
      case msg: JsValue =>
        val res = msg.validate[PlayRequest].fold(
          invalid => PlayResponse(accepted = None, errors = Some(JsError.toJson(invalid))),
          valid => play(valid))
        out ! Json.toJson(res)
    }
  }
}
