package controllers

import java.io.ByteArrayInputStream
import javax.inject.Inject

import akka.stream.scaladsl.StreamConverters
import domain.service.ServiceContext
import modules.ServiceContextModule
import play.api.mvc.{Action, AnyContent, Controller}

class MainController @Inject() (context: ServiceContext) extends Controller {
  val index: Action[AnyContent] = Action(_ => Ok(views.html.MainController.main()))

  val download: Action[AnyContent] = Action { _ =>
    val CHUNK_SIZE = 100
    val data = () => new ByteArrayInputStream(context.write.getBytes("UTF-8"))
    val source = StreamConverters.fromInputStream(data, CHUNK_SIZE)
    Ok.chunked(source).
      withHeaders("Content-Disposition" -> s"attachment; filename=${ServiceContextModule.defaultDatabaseFilename}")
  }
}
