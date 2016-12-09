package controllers

import domain.service.ServiceContext
import models.ProtobufMutation
import play.api.libs.json.Reads
import play.api.mvc.Call
import protobuf.core.{Attributes, Name}
import protobuf.entity.Entity
import views.html

abstract class AttributesController[A, B <: ProtobufMutation[A]](context: ServiceContext)
                                                                (implicit reads: Reads[B], entity: Entity[A])
  extends OpsController[A, B](context) {

  protected[this] def tupled(a: A): (Name, Attributes)

  protected[this] val setCall: Call

  protected[this] val getCall: String => Call

  protected[this] val deleteCall: String => Call

  override protected[this] def list(a: List[A]): HtmlContent = {
    html.AttributesController.list(a.map(tupled), setCall, getCall)
  }

  override protected[this] def get(a: A): HtmlContent = {
    html.AttributesController.get(tupled(a), setCall, getCall, deleteCall)
  }
}
