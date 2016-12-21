package controllers

import java.io.Reader
import javax.inject.Inject

import domain.ops.Ops
import domain.service.ServiceContext
import play.api.mvc.Call
import protobuf.core.{NamedElements, NamedElementsOuterClass}
import views.html

class ElementsController @Inject() (context: ServiceContext)
  extends OpsController[NamedElements](context) with FixedOps[NamedElements] {

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

  override protected[this] def builder(json: Reader): NamedElements = {
    NamedElementsOuterClass.NamedElements.newBuilder().parse(json, b => NamedElements.fromJavaProto(b.build()))
  }
}
