package controllers

import domain.service.ServiceContext
import models.setter.core.NamedAttributesSetter
import play.api.mvc.Call
import protobuf.core.NamedAttributes
import views.html

abstract class AttributesController(context: ServiceContext)
  extends OpsController[NamedAttributes, NamedAttributesSetter](context) {

  protected[this] val setCall: Call

  protected[this] val getCall: String => Call

  protected[this] val deleteCall: String => Call

  override protected[this] def list(a: List[NamedAttributes]): HtmlContent = {
    html.AttributesController.list(a, setCall, getCall)
  }

  override protected[this] def get(a: NamedAttributes): HtmlContent = {
    html.AttributesController.get(a, setCall, getCall, deleteCall)
  }
}
