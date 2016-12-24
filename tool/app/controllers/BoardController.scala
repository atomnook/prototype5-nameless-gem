package controllers

import java.util.UUID
import javax.inject.Inject

import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props}
import akka.stream.Materializer
import board.actor.BoardActor
import board.message.BoardMessage.{BoardReady, FallIntoErrorState}
import board.message.EscalatedBoardMessage
import board.setting.{BoardSetting, Lines => BoardLines}
import controllers.BoardController.PlayActor
import domain.service.ServiceContext
import models.value.Identifier
import play.api.libs.json._
import play.api.libs.streams.ActorFlow
import play.api.mvc.{Action, AnyContent, Controller, WebSocket}
import views.html

class BoardController @Inject() (implicit context: ServiceContext, system: ActorSystem, materializer: Materializer)
  extends Controller {

  val board: Action[AnyContent] = Action(_ => Ok(html.BoardController.board()))

  val play: WebSocket = WebSocket.accept[JsValue, JsValue] { _ =>
    ActorFlow.actorRef(out => Props(new PlayActor(out, context)))
  }
}

object BoardController {
  private[this] case class Lines(front: List[Identifier], rear: List[Identifier]) {
    def setting: BoardLines = BoardLines()
  }

  private[this] case class PlayRequest(friends: Lines, enemies: Lines)

  private[this] case class PlayResponse(accepted: Option[String], errors: Option[JsValue])

  private[this] case class PlayMessage(message: String)

  private[this] implicit val linesFormat: OFormat[Lines] = Json.format[Lines]

  private[this] implicit val requestFormat: OFormat[PlayRequest] = Json.format[PlayRequest]

  private[this] implicit val responseFormat: OFormat[PlayResponse] = Json.format[PlayResponse]

  private[this] implicit val messageFormat: OFormat[PlayMessage] = Json.format[PlayMessage]

  private class PlayActor(out: ActorRef, serviceContext: ServiceContext) extends Actor {
    private[this] var playId: Option[UUID] = None

    private[this] var child: Option[ActorRef] = None

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

          val setting = BoardSetting(
            id = id,
            database = serviceContext.database.get(),
            friends = request.friends.setting,
            enemies = request.enemies.setting)

          playId = Some(id)

          child = Some(context.actorOf(BoardActor.props(setting), name = s"board-$id"))

          send(s"$id started.")

          PlayResponse(accepted = playId.map(_.toString), errors = None)
      }
    }

    private[this] def handle(msg: EscalatedBoardMessage): Unit = {
      msg match {
        case BoardReady =>
          send("ready to play.")

        case FallIntoErrorState =>
          send("connection closed in error state.")
          self ! PoisonPill
      }
    }

    override def receive: Receive = {
      case msg: JsValue =>
        val res = msg.validate[PlayRequest].fold(
          invalid => PlayResponse(accepted = None, errors = Some(JsError.toJson(invalid))),
          valid => play(valid))
        out ! Json.toJson(res)

      case msg: EscalatedBoardMessage => handle(msg)
    }
  }
}
