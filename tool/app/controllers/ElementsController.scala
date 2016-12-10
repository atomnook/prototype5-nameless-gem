package controllers

import javax.inject.Inject

import domain.ops.Ops
import domain.service.ServiceContext
import models.setter.core.NamedElementsSetter
import protobuf.core.NamedElements
import views.html

class ElementsController @Inject() (context: ServiceContext)
  extends OpsController[NamedElements, NamedElementsSetter](context) {

  override protected[this] def list(a: List[NamedElements]): HtmlContent = html.ElementsController.list(a)

  override protected[this] def get(a: NamedElements): HtmlContent = html.ElementsController.get(a)

  override protected[this] val ops: Ops[NamedElements] = service.elemental
}
