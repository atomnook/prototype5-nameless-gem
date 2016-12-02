package helpers

import domain.service.{DatabaseService, ServiceContext}
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.{AllBrowsersPerSuite, ChromeInfo, OneServerPerSuite, PlaySpec}
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

trait ToolSpec extends PlaySpec with OneServerPerSuite with AllBrowsersPerSuite with BeforeAndAfterEach {
  override lazy val browsers = Vector(ChromeInfo)

  implicit override lazy val app: Application = {
    new GuiceApplicationBuilder().configure("database.init.enable " -> false).build()
  }

  protected[this] val service = DatabaseService(app.injector.instanceOf(classOf[ServiceContext]))

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    app.injector.instanceOf(classOf[ServiceContext]).clear()
  }
}
