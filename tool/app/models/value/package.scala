package models

import com.trueaccord.scalapb.{GeneratedEnum, GeneratedEnumCompanion}
import play.api.data.validation.ValidationError
import play.api.libs.json._
import protobuf.Name

package object value {
  private[this] def enum[A <: GeneratedEnum](c: GeneratedEnumCompanion[A]): Format[A] = {
    new Format[A] {
      override def writes(o: A): JsValue = JsNumber(o.value)

      override def reads(json: JsValue): JsResult[A] = {
        json.validate[String].fold(
          JsError(_),
          valid => c.fromName(valid).map(JsSuccess(_)).getOrElse(JsError(ValidationError("unrecognized value", valid))))
      }
    }
  }

  implicit val name: Format[Name] = enum(Name)
}
