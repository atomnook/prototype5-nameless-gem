package models.value

import play.api.data.validation.ValidationError
import play.api.libs.json._

case class Identifier(id: String)

object Identifier {
  private[this] val regex = "([a-zA-Z0-9_\\-]+)".r

  implicit val format = new Format[Identifier] {
    override def reads(json: JsValue): JsResult[Identifier] = {
      json.validate[String].fold(JsError(_), {
        case regex(s) => JsSuccess(Identifier(s))
        case s => JsError(ValidationError(s"unmatched $regex", s))
      })
    }

    override def writes(o: Identifier): JsValue = JsString(o.id)
  }
}
