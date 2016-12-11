package controllers

import models.ProtobufMutation
import play.api.mvc.Call
import views.html

trait FixedOps[A, B <: ProtobufMutation[A]] extends OpsController[A, B] {
  protected[this] def table(a: List[A]): HtmlContent => HtmlContent

  protected[this] def input(a: Option[A]): HtmlContent

  protected[this] def json(id: Option[String]): HtmlContent

  protected[this] val setCall: Call

  protected[this] def deleteCall(id: String): Call

  override protected[this] def list(a: List[A]): HtmlContent = {
    html.Ops.list(table = table(a), input = input(None), json = json(None), set = setCall)
  }

  override protected[this] def get(a: A): HtmlContent = {
    html.Ops.get(
      table = table(a :: Nil),
      input = input(Some(a)),
      updateJson = json(Some(entity.id(a))),
      copyJson = json(None),
      set = setCall,
      delete = deleteCall(entity.id(a)))
  }
}
