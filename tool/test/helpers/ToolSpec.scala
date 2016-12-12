package helpers

import domain.service.{DatabaseService, ServiceContext}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatestplus.play._
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

trait ToolSpec
  extends PlaySpec
    with OneServerPerSuite with OneBrowserPerSuite with FirefoxFactory with BeforeAndAfterEach with BeforeAndAfterAll {

  implicit override lazy val app: Application = {
    new GuiceApplicationBuilder().configure("database.init.enable " -> false).build()
  }

  protected[this] val context: ServiceContext = app.injector.instanceOf(classOf[ServiceContext])

  protected[this] val service = DatabaseService(context)

  protected[this] val browser = FirefoxInfo(firefoxProfile)

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    context.clear()
  }

  override protected def afterAll(): Unit = {
    super.afterAll()
    webDriver.quit()
  }
}
