package controllers

import controllers.OpsController.OpsResult
import domain.ops.Ops
import domain.service.{DatabaseService, ServiceContext}
import models.ProtobufMutation
import play.api.libs.json.{Json, OFormat, Reads}
import play.api.mvc.{Action, AnyContent, Controller}
import play.twirl.api.HtmlFormat
import protobuf.entity.Entity

abstract class OpsController[A, B <: ProtobufMutation[A] : Reads](context: ServiceContext)
                                                                 (implicit entity: Entity[A]) extends Controller {
  protected[this] type HtmlContent = HtmlFormat.Appendable

  protected[this] val service = DatabaseService(context)

  protected[this] val ops: Ops[A]

  protected[this] def list(a: List[A]): HtmlContent

  protected[this] def get(a: A): HtmlContent

  val list: Action[AnyContent] = Action(_ => Ok(list(ops.get)))

  def get(id: String): Action[AnyContent] = Action { _ =>
    ops.get.find(value => entity.equal(value, entity.identity(id))) match {
      case Some(value) => Ok(get(value))
      case None => Ok(views.html.Pages.notFound(s"$id not found"))
    }
  }

  val set: Action[B] = Action(parse.json[B]) { request =>
    ops.set(request.body.toProtobuf)
    Ok(Json.toJson(OpsResult(status = OK)))
  }

  def delete(id: String) = Action { _ =>
    ops.delete(entity.identity(id))
    Ok(Json.toJson(OpsResult(status = OK)))
  }
}

object OpsController {
  case class OpsResult(status: Int, error: Option[String])

  object OpsResult {
    def apply(status: Int): OpsResult = OpsResult(status = status, error = None)
  }

  implicit val format: OFormat[OpsResult] = Json.format[OpsResult]
}
