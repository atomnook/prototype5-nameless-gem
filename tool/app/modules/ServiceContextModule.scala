package modules

import com.google.inject.AbstractModule
import domain.service.ServiceContext
import play.api.{Configuration, Environment, Logger}

import scala.io.Source

class ServiceContextModule(environment: Environment, configuration: Configuration) extends AbstractModule {
  override def configure(): Unit = {
    val enable = configuration.getBoolean("database.init.enable").getOrElse(false)
    if (enable) {
      val database = configuration.getString("database.init.file").
        getOrElse(ServiceContextModule.defaultDatabaseFilename)
      environment.resourceAsStream(database) match {
        case Some(stream) =>
          val base64 = Source.fromInputStream(stream, "UTF-8").mkString
          val head = base64.take(100)
          val ellipsis = if (base64.length > 100) "..." else ""
          Logger.info(s"database($head$ellipsis) (${base64.length})")

          bind(classOf[ServiceContext]).toInstance(ServiceContext(base64))

        case None =>
          Logger.info(s"$database not found")
          bind(classOf[ServiceContext]).toInstance(ServiceContext())
      }
    } else {
      bind(classOf[ServiceContext]).toInstance(ServiceContext())
    }
  }
}

object ServiceContextModule {
  val defaultDatabaseFilename = "database"
}
