package controllers

import java.io.Reader

import akka.stream.scaladsl.Sink
import akka.util.ByteString
import com.google.protobuf.Message
import com.google.protobuf.util.JsonFormat
import com.trueaccord.scalapb.GeneratedMessage
import controllers.OpsApi.OpsResult
import domain.ops.Ops
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{Json, OFormat}
import play.api.libs.streams.Accumulator
import play.api.mvc.{Action, BodyParser, Controller}
import protobuf.entity.Entity

import scala.concurrent.Future
import scala.io.Source

trait OpsApi[A <: GeneratedMessage] { this: Controller =>
  protected[this] val ops: Ops[A]

  protected[this] implicit class BuilderToProto[B <: Message.Builder](b: B) {
    def parse[A](json: Reader, f: B => A): A = {
      JsonFormat.parser().merge(json, b)
      f(b)
    }
  }

  implicit protected[this] val entity: Entity[A]

  protected[this] def builder(json: Reader): A

  private[this] val json: BodyParser[A] = {
    BodyParser { _ =>
      val sink = Sink.fold[ByteString, ByteString](ByteString.empty)((state, bs) => state ++ bs)
      Accumulator(sink).mapFuture { bytes =>
        try {
          val reader = Source.fromInputStream(bytes.iterator.asInputStream).bufferedReader()
          Future.successful(Right(builder(reader)))
        } catch {
          case e: RuntimeException =>
            val message = e.getMessage
            Future.successful(Left(BadRequest(Json.toJson(OpsResult(status = BAD_REQUEST, error = Some(message))))))
        }
      }
    }
  }

  val set: Action[A] = Action(json) { request =>
    ops.set(request.body)
    Ok(Json.toJson(OpsResult(status = OK)))
  }

  def delete(id: String) = Action { _ =>
    ops.delete(entity.identity(id))
    Ok(Json.toJson(OpsResult(status = OK)))
  }
}

object OpsApi {
  case class OpsResult(status: Int, error: Option[String])

  object OpsResult {
    def apply(status: Int): OpsResult = OpsResult(status = status, error = None)
  }

  implicit val format: OFormat[OpsResult] = Json.format[OpsResult]
}
