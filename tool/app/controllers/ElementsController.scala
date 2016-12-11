package controllers

import javax.inject.Inject

import domain.ops.Ops
import domain.service.ServiceContext
import models.setter.core.NamedElementsSetter
import play.api.mvc.Call
import protobuf.core.NamedElements
import views.html

class ElementsController @Inject() (context: ServiceContext)
  extends OpsController[NamedElements, NamedElementsSetter](context) with FixedOps[NamedElements, NamedElementsSetter] {

  override protected[this] def table(a: List[NamedElements]): (HtmlContent) => HtmlContent = {
    html.ElementsController.table(a)
  }

  override protected[this] def input(a: Option[NamedElements]): HtmlContent = html.ElementsController.input(a)

  override protected[this] def json(id: Option[String]): HtmlContent = {
    html.ElementsController.json(id)
  }

  override protected[this] val setCall: Call = routes.ElementsController.set()

  override protected[this] def deleteCall(id: String): Call = routes.ElementsController.delete(id)

  override protected[this] val ops: Ops[NamedElements] = service.elemental
}
