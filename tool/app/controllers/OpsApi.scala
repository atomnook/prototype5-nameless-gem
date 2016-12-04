package controllers

import controllers.OpsApi.OpsResult
import domain.ops.Ops
import models.ProtobufMutation
import play.api.libs.json.{Json, OFormat, Reads}
import play.api.mvc.{Action, Controller}
import protobuf.entity.Entity

trait OpsApi[A, B <: ProtobufMutation[A]] { this: Controller =>
  protected[this] val ops: Ops[A]

  implicit protected[this] val reads: Reads[B]

  implicit protected[this] val entity: Entity[A]

  val set: Action[B] = Action(parse.json[B]) { request =>
    ops.set(request.body.toProtobuf)
    Ok(Json.toJson(OpsResult(status = OK)))
  }

  def delete(id: String) = Action { _ =>
    ops.delete(entity.identity(id))
    Ok(Json.toJson(OpsResult(status = OK)))
  }
}

object OpsApi {
  case class OpsResult(status: Int, error: Option[String])

  object OpsResult {
    def apply(status: Int): OpsResult = OpsResult(status = status, error = None)
  }

  implicit val format: OFormat[OpsResult] = Json.format[OpsResult]
}
