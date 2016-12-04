package models.value

import com.trueaccord.scalapb.{GeneratedEnum, GeneratedEnumCompanion}
import play.api.data.validation.ValidationError
import play.api.libs.json._

trait EnumValue[A <: GeneratedEnum] {
  val value: A
}

object EnumValue {
  private[value] def format[A <: GeneratedEnum, B <: EnumValue[A]](c: GeneratedEnumCompanion[A], apply: A => B): Format[B] = {
    new Format[B] {
      override def writes(o: B): JsValue = JsString(o.value.name)

      override def reads(json: JsValue): JsResult[B] = {
        val a = json.validate[String].fold(
          JsError(_),
          valid =>
            c.fromName(valid).map(JsSuccess(_)).
              getOrElse(JsError(ValidationError("unrecognized value", valid))))
        a.map(apply)
      }
    }
  }
}
