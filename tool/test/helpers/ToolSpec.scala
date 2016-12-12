package helpers

import domain.service.{DatabaseService, ServiceContext}
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play._
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

trait ToolSpec extends PlaySpec with OneServerPerSuite with OneBrowserPerSuite with BeforeAndAfterEach {
  implicit override lazy val app: Application = {
    new GuiceApplicationBuilder().configure("database.init.enable " -> false).build()
  }

  protected[this] val context: ServiceContext = app.injector.instanceOf(classOf[ServiceContext])

  protected[this] val service = DatabaseService(context)

  protected[this] val browser: BrowserInfo

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    context.clear()
  }
}
