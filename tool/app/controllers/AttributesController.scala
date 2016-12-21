package controllers

import java.io.Reader

import domain.service.ServiceContext
import play.api.mvc.Call
import protobuf.core.{NamedAttributes, NamedAttributesOuterClass}
import views.html

abstract class AttributesController(context: ServiceContext)
  extends OpsController[NamedAttributes](context) with FixedOps[NamedAttributes] {

  protected[this] def getCall(id: String): Call

  override protected[this] def table(a: List[NamedAttributes]): (HtmlContent) => HtmlContent = {
    html.AttributesController.table(values = a, get = getCall)
  }

  override protected[this] def input(a: Option[NamedAttributes]): HtmlContent = html.AttributesController.input(a)

  override protected[this] def json(id: Option[String]): HtmlContent = {
    html.AttributesController.json(id)
  }

  override protected[this] def builder(json: Reader): NamedAttributes = {
    NamedAttributesOuterClass.NamedAttributes.newBuilder().parse(json, b => NamedAttributes.fromJavaProto(b.build()))
  }
}
