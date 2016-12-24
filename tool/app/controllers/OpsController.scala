package controllers

import com.trueaccord.scalapb.GeneratedMessage
import domain.service.{DatabaseService, ServiceContext}
import play.api.mvc.{Action, AnyContent, Controller}
import play.twirl.api.HtmlFormat
import protobuf.entity.Entity

abstract class OpsController[A <: GeneratedMessage](context: ServiceContext)
                                                   (override implicit protected[this] val entity: Entity[A])
  extends Controller with OpsApi[A] {

  protected[this] type HtmlContent = HtmlFormat.Appendable

  protected[this] val service = DatabaseService(context)

  protected[this] def list(a: List[A]): HtmlContent

  protected[this] def get(a: A): HtmlContent

  val list: Action[AnyContent] = Action(_ => Ok(list(ops.get)))

  def get(id: String): Action[AnyContent] = Action { _ =>
    ops.get.find(value => entity.equal(value, entity.identity(id))) match {
      case Some(value) => Ok(get(value))
      case None => Ok(views.html.Pages.notFound(s"$id not found"))
    }
  }
}
