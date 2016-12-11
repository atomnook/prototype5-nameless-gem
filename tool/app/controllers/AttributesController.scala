package controllers

import domain.service.ServiceContext
import models.setter.core.NamedAttributesSetter
import play.api.mvc.Call
import protobuf.core.NamedAttributes
import views.html

abstract class AttributesController(context: ServiceContext)
  extends OpsController[NamedAttributes, NamedAttributesSetter](context) with FixedOps[NamedAttributes, NamedAttributesSetter] {

  protected[this] def getCall(id: String): Call

  override protected[this] def table(a: List[NamedAttributes]): (HtmlContent) => HtmlContent = {
    html.AttributesController.table(values = a, get = getCall)
  }

  override protected[this] def input(a: Option[NamedAttributes]): HtmlContent = html.AttributesController.input(a)

  override protected[this] def json(id: Option[String]): HtmlContent = {
    html.AttributesController.json(id)
  }
}
